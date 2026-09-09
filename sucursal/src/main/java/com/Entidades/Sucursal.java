package com.Entidades;

import com.example.Caja_de_Herramientas.Arboles.AVLImpl;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;
import com.example.Caja_de_Herramientas.Lista.*;

public class Sucursal {
    private String id;
    private ListaArray<Sector> sectores;
    private HistorialInteracciones historialInteracciones;
    private CopiaDocumentos copiaDocumentos;
    private AVLImpl<Cliente> indiceClientes; 
    private FormulaComision formulaComisionActual;
    private final ServicioLiquidacionComisiones servicioLiquidacion = new ServicioLiquidacionComisiones();

    public Sucursal(String id) {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        this.id = id;
        this.sectores = new ListaArray<>();
        this.indiceClientes = new AVLImpl<>();
        this.copiaDocumentos = new CopiaDocumentos();
        this.historialInteracciones = new HistorialInteracciones();
    }

    public void agregarSector(Sector sector) {
        if (sector == null) throw new IllegalArgumentException("El sector no puede ser null");
        sectores.agregar(sector);
    }

    public void registrarInteraccion(Interaccion interaccion) {
        historialInteracciones.registrarInteraccion(interaccion);
    }

    public void registrarDocumento(Documento documento) {
        copiaDocumentos.registrarDocumento(documento);
    }

    // Búsqueda O(log n) gracias al arbol AVL
    public Cliente buscarCliente(String ci) {
        if (ci == null) return null;
        Cliente client = new Cliente(ci);
        // El árbol devuelve directamente el objeto Cliente encontrado
        return indiceClientes.buscar(client);
    }

    public Pila<Interaccion> obtenerHistorialCliente(String ci) {
        Cliente cliente = buscarCliente(ci); // <- Invocación O(log n)
        if (cliente == null) throw new IllegalArgumentException("El cliente no existe");
        return historialInteracciones.obtenerPorCliente(cliente.getCi());
    }

    public Pila<Documento> obtenerDocumentosCliente(String ci) {
        Cliente cliente = buscarCliente(ci); // <- Invocación O(log n)
        if (cliente == null) throw new IllegalArgumentException("El cliente no existe");
        return copiaDocumentos.obtenerPorCliente(cliente);
    }

    public Pila<Documento> obtenerDocumentosVencidosCliente(String ci) {
        Cliente cliente = buscarCliente(ci); // <- Invocación O(log n)
        if (cliente == null) throw new IllegalArgumentException("El cliente no existe");
        return copiaDocumentos.obtenerVencidosPorCliente(cliente);
    }

    public ListaEnlazada<ConteoInteraccion> obtenerConteoInteraccionesPorTipo() {
        return historialInteracciones.contarPorTipo();
    }

    public void registrarClienteEnSector(Cliente cliente, Sector sector, NivelPrioridad prioridad, SolicitudAtencion solicitud) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser null");
        if (sector == null) throw new IllegalArgumentException("El sector no puede ser null");
        if (prioridad == null) throw new IllegalArgumentException("La prioridad no puede ser null");
        if (solicitud == null) throw new IllegalArgumentException("La solicitud no puede ser null");

        if (!sectores.contiene(sector)) {
            throw new IllegalArgumentException("El sector no esta registrado en la sucursal");
        }

        // Se inserta en el índice AVL en O(log n). Si ya existía, insertar() devuelve false sin romper la estructura.
        indiceClientes.insertar(cliente);
        sector.recibirCliente(cliente, prioridad, solicitud);
    }

    public boolean altaProductoACliente(String ciCliente, IProducto producto, String mostradorId) {
    if (ciCliente == null || producto == null || this.indiceClientes == null) {
        return false;
    }

    Cliente cliente = this.indiceClientes.buscar(new Cliente(ciCliente)); //O(log n) se busca en el arbol AVL
    if (cliente == null) {
        return false;
    }

    cliente.agregarProducto(producto);

    if (this.historialInteracciones != null) {
        this.historialInteracciones.registrarInteraccion(new Interaccion(TipoInteraccion.ALTA_PRODUCTO,cliente.getCi(),mostradorId));
    }

    return true;
    }

    //Remueve un producto o paquete de un cliente utilizando el ID del producto.
    //Registra la operación como una interacción de tipo BAJA_PRODUCTO.
    public boolean bajaProductoACliente(String ciCliente, String idProducto, String mostradorId) {
        if (ciCliente == null || idProducto == null || this.indiceClientes == null) {
            return false;
        }

        Cliente cliente = this.indiceClientes.buscar(new Cliente(ciCliente));
        if (cliente == null) {
            return false;
        }

        boolean removido = cliente.quitarProductoEnCartera(idProducto);

        if (removido && this.historialInteracciones != null) {
            this.historialInteracciones.registrarInteraccion(new Interaccion(
                TipoInteraccion.BAJA_PRODUCTO,
                cliente.getCi(),
                mostradorId
            ));
        }

        return removido;
    }
    public void cargarFormulaComision(String id, String textoFormula) {
        this.formulaComisionActual = new FormulaComision(id, textoFormula);
    }

    // Retorna la fórmula actual limpia (sin paréntesis redundantes mediante inOrder)
    public String obtenerFormulaVigenteLimpia() {
        if (this.formulaComisionActual == null) return "";
        return this.formulaComisionActual.obtenerFormulaLimpia();
    }

    // Ejecuta la liquidación real impactando en el historial
    public ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> liquidarComisionesVigentes() {
        return this.servicioLiquidacion.liquidar(this.indiceClientes, this.formulaComisionActual, this.historialInteracciones);
    }

    // Realiza la simulación sin alterar historial ni estado
    public ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> simularNuevaFormula(String id, String textoFormulaSimulada) {
        FormulaComision formulaSimulada = new FormulaComision(id, textoFormulaSimulada);
        return this.servicioLiquidacion.simular(this.indiceClientes, formulaSimulada);
    }


    //Carga o actualiza la fórmula de comisión vigente en la sucursal y registra el evento en el historial de auditoría.
    
    public void cargarFormulaComision(String id, String textoFormula, String mostradorId) {
        this.formulaComisionActual = new FormulaComision(id, textoFormula);

        // Auditoría del cambio de fórmula
        if (this.historialInteracciones != null) {
            this.historialInteracciones.registrarInteraccion(new Interaccion(
                TipoInteraccion.MODIFICACION,
                id,          // El ID de la fórmula que estás cargando
                mostradorId  // El ID del mostrador/operador que realiza el cambio
            ));
        }
    }

    public Cliente buscarClientePorCi(String ci) {
        if (ci == null || this.indiceClientes == null) {
            return null;
        }
        return this.indiceClientes.buscar(new Cliente(ci));
    }


    //Busca un producto por su número de cuenta/ID dentro de la cartera del cliente.
    //Primero localiza al cliente en O(log n) y luego busca el producto en su lista.

    public IProducto buscarProductoPorCuenta(String ciCliente, String numeroCuenta) {
        if (ciCliente == null || numeroCuenta == null) {
            return null;
        }
        
        // Búsqueda eficiente del cliente por CI
        Cliente cliente = buscarClientePorCi(ciCliente);
        if (cliente == null) {
            return null;
        }
        
        // Búsqueda del producto dentro del cliente utilizando su ID
        return cliente.buscarProductoEnCartera(numeroCuenta);
    }



    //Retorna la cartera de clientes ordenada por CI.
    //Aprovecha la propiedad In-Order del árbol AVL, obteniendo la lista ordenada de forma natural.

    public ListaEnlazada<Cliente> listarCarteraOrdenadaPorCi() {
        ListaEnlazada<Cliente> carteraOrdenada = new ListaEnlazada<>();
        
        if (this.indiceClientes != null) {
            this.indiceClientes.inOrder(cliente -> {
                carteraOrdenada.agregar(cliente);
            });
        }
        
        return carteraOrdenada;
    }
    // clientes por rango de documento que dado un rango de documentos [desde, hasta], devuelve todos los clientes registrados cuyo ci cae dentro de ese rango, ordenados por ci

    public TDALista<Cliente> obtenerClientesEnRangoDeDocumento(String desde, String hasta) {

        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Los limites del rango no pueden ser nulos");
        }
        // "desde" y "hasta" se convierten en Clientes de consulta para poder
        // compararlos por CI con los clientes almacenados en el árbol.
        return indiceClientes.enRango(new Cliente(desde), new Cliente(hasta));
    }
    
    // clientes vecinos por documento
    
    public Cliente obtenerClienteConDocumentoAnterior(String ci) {

        if (ci == null) {
            throw new IllegalArgumentException("El ci no puede ser nulo");
        }

        return indiceClientes.predecesor(new Cliente(ci));
    }

    // Simétrico al anterior: encuentra el cliente registrado con el
    // documento inmediatamente SIGUIENTE al ci dado, baja un solo camino del AVL.
    public Cliente obtenerClienteConDocumentoSiguiente(String ci) {

        if (ci == null) {
            throw new IllegalArgumentException("El ci no puede ser nulo");
        }

        return indiceClientes.sucesor(new Cliente(ci));
    }
}