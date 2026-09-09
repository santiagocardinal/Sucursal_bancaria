package com.example;

import java.time.LocalDate;

import com.Entidades.Cliente;
import com.Entidades.ConteoInteraccion;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Interaccion;
import com.Entidades.Mostrador;
import com.Entidades.PaqueteProducto;
import com.Entidades.Sector;
import com.Entidades.ServicioLiquidacionComisiones;
import com.Entidades.Sucursal;
import com.Entidades.TarjetaDeCredito;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.AtencionCuentasPersonales;
import com.example.EstrategiasDeAtencion.AtencionEjecutivos;
import com.example.EstrategiasDeAtencion.AtencionPrestamos;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class App {

    private static int contadorDocumentos = 1;

    public static void main(String[] args) {

        Sucursal sucursal = new Sucursal("SUC-CENTRO");

        Sector sectorCuentas = new Sector(sucursal);
        Sector sectorEjecutivos = new Sector(sucursal);
        Sector sectorPrestamos = new Sector(sucursal);

        sucursal.agregarSector(sectorCuentas);
        sucursal.agregarSector(sectorEjecutivos);
        sucursal.agregarSector(sectorPrestamos);

        Mostrador mostradorCuentas = new Mostrador("MOST-CTA-01", sectorCuentas, new AtencionCuentasPersonales(sucursal), true);
        Mostrador mostradorEjecutivos = new Mostrador("MOST-EJE-01", sectorEjecutivos, new AtencionEjecutivos(sucursal), true);
        Mostrador mostradorPrestamos = new Mostrador("MOST-PRE-01", sectorPrestamos, new AtencionPrestamos(sucursal), true);

        sectorCuentas.agregarMostrador(mostradorCuentas);
        sectorEjecutivos.agregarMostrador(mostradorEjecutivos);
        sectorPrestamos.agregarMostrador(mostradorPrestamos);

        System.out.println("Alta de clientes");

        Cliente maria = new Cliente("20123456");
        Cliente lucia = new Cliente("40345678");
        Cliente ana = new Cliente("60567890");
        Cliente diego = new Cliente("22000000");
        Cliente valentina = new Cliente("25000000");

        ListaEnlazada<Documento> docsMaria = new ListaEnlazada<>();
        docsMaria.agregar(crearDocumento(TipoDocumento.CEDULA_IDENTIDAD, maria, LocalDate.now(), null));
        docsMaria.agregar(crearDocumento(TipoDocumento.COMPROBANTE_INGRESOS, maria, LocalDate.now().minusYears(2), LocalDate.now().minusYears(1)));

        SolicitudAtencion solMaria = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, null, 15000, docsMaria);
        SolicitudAtencion solLucia = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());
        SolicitudAtencion solAna = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());
        SolicitudAtencion solDiego = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());
        SolicitudAtencion solValentina = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());

        sucursal.registrarClienteEnSector(maria, sectorCuentas, NivelPrioridad.NORMAL, solMaria);
        sucursal.registrarClienteEnSector(lucia, sectorCuentas, NivelPrioridad.NORMAL, solLucia);
        sucursal.registrarClienteEnSector(ana, sectorCuentas, NivelPrioridad.NORMAL, solAna);
        sucursal.registrarClienteEnSector(diego, sectorEjecutivos, NivelPrioridad.NORMAL, solDiego);
        sucursal.registrarClienteEnSector(valentina, sectorEjecutivos, NivelPrioridad.NORMAL, solValentina);

        Cuenta cuentaLucia = new Cuenta("CTA-" + lucia.getCi(), 50000, Moneda.PESO_URUGUAYO);
        cuentaLucia.modificarEstado(EstadoProducto.VENCIDO);
        lucia.agregarProducto(cuentaLucia);

        sectorCuentas.llamarClienteAMostrador();
        mostradorCuentas.liberar();

        Cuenta cuentaValentina = new Cuenta("CTA-" + valentina.getCi(), 8000, Moneda.PESO_URUGUAYO);
        valentina.agregarProducto(cuentaValentina);

        System.out.println("Clientes dados de alta: \n" + maria.getCi() + "\n" + lucia.getCi() + "\n" + ana.getCi() + "\n" + diego.getCi() + "\n" + valentina.getCi());

        System.out.println();
        System.out.println("Consulta 1 - Clientes por rango de documento [21000000, 26000000] ");
        TDALista<Cliente> enRango = sucursal.obtenerClientesEnRangoDeDocumento("21000000", "26000000");
        for (int i = 0; i < enRango.tamano(); i++) {
            System.out.println("   - " + enRango.obtener(i).getCi());
        }
        System.out.println();
        System.out.println("Consulta 2 - Documento vecino de \"23000000\" (no registrado)");
        Cliente anterior = sucursal.obtenerClienteConDocumentoAnterior("23000000");
        Cliente siguiente = sucursal.obtenerClienteConDocumentoSiguiente("23000000");
        System.out.println("Anterior: " + (anterior != null ? anterior.getCi() : "ninguno"));
        System.out.println("Siguiente: " + (siguiente != null ? siguiente.getCi() : "ninguno"));

        System.out.println();
        System.out.println("Productos compuestos ");

        PaqueteProducto paqueteDiego = new PaqueteProducto("PAQ-DIEGO", Moneda.PESO_URUGUAYO);
        Cuenta ctaDiego1 = new Cuenta("CTA-DIEGO-1", 15000, Moneda.PESO_URUGUAYO);
        Cuenta ctaDiego2 = new Cuenta("CTA-DIEGO-2", 47000, Moneda.PESO_URUGUAYO);

        PaqueteProducto subPaqueteDiego = new PaqueteProducto("SUBPAQ-DIEGO", Moneda.PESO_URUGUAYO);
        TarjetaDeCredito tcDiego1 = new TarjetaDeCredito("TC-DIEGO-1", Moneda.PESO_URUGUAYO, 30000f);
        tcDiego1.setSaldo(12000f); // ya consumió $18.000 del límite
        Cuenta ctaDiego3 = new Cuenta("CTA-DIEGO-3", 5000, Moneda.PESO_URUGUAYO);

        subPaqueteDiego.agregarComponente(tcDiego1);
        subPaqueteDiego.agregarComponente(ctaDiego3);

        paqueteDiego.agregarComponente(ctaDiego1);
        paqueteDiego.agregarComponente(ctaDiego2);
        paqueteDiego.agregarComponente(subPaqueteDiego);

        // altaProductoACliente ya se encarga de auditar el alta (ALTA_PRODUCTO)
        sucursal.altaProductoACliente(diego.getCi(), paqueteDiego, mostradorEjecutivos.getId());

        System.out.println("Árbol de productos de Diego:");
        imprimirArbolProducto(paqueteDiego, 1);
        System.out.println("¿PAQ-DIEGO es hoja? " + paqueteDiego.esHoja());
        System.out.println("Precio a pagar total del paquete (suma de todos los niveles): " + paqueteDiego.getPrecioAPagar());

        System.out.println();
        System.out.println("Consulta 3 - Componente de mayor saldo del paquete");
        Cuenta mayorSaldo = paqueteDiego.obtenerComponenteDeMayorSaldo();
        System.out.println("Cuenta con mayor saldo dentro del paquete de Diego: "
                + (mayorSaldo != null ? mayorSaldo.getId() + " ($" + mayorSaldo.getSaldo() + ")" : "ninguna"));

        System.out.println();
        System.out.println("Consulta 4 - Producto por número de cuenta ");
        IProducto encontrado = sucursal.buscarProductoPorCuenta(diego.getCi(), "TC-DIEGO-1");
        System.out.println("Búsqueda de \"TC-DIEGO-1\" (anidado 2 niveles adentro del paquete): "
                + (encontrado != null ? "ENCONTRADO -> " + encontrado.getId() : "no encontrado"));

        System.out.println();
        System.out.println("Consulta 6 - Cartera de clientes ordenada por CI ");
        ListaEnlazada<Cliente> carteraOrdenada = sucursal.listarCarteraOrdenadaPorCi();
        for (int i = 0; i < carteraOrdenada.tamano(); i++) {
            System.out.println("   - " + carteraOrdenada.obtener(i).getCi());
        }

        System.out.println();
        System.out.println("Baja de productos");
        System.out.println();

        boolean bajaNestedViaSucursal = sucursal.bajaProductoACliente(diego.getCi(), "CTA-DIEGO-3", mostradorEjecutivos.getId());
        System.out.println("Baja de \"CTA-DIEGO-3\" (anidada, 2 niveles adentro de PAQ-DIEGO) vía Sucursal.bajaProductoACliente(): "
                + bajaNestedViaSucursal);

        boolean bajaCascada = sucursal.bajaProductoACliente(diego.getCi(), "PAQ-DIEGO", mostradorEjecutivos.getId());
        System.out.println("Baja en cascada de \"PAQ-DIEGO\" (nodo padre, primer nivel): " + bajaCascada
                + " -> productos restantes de Diego: " + diego.obtenerProductos().tamano());
       
        System.out.println();
        System.out.println("Fórmulas de comisión, liquidación y simulación");

        // Se da de alta un préstamo más para que la liquidación tenga
        // varios clientes con productos sobre los que calcular
        SolicitudAtencion altaPrestamoAna = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "IGNORADO", 80000, new ListaEnlazada<>());
        ana.setSolicitudActual(altaPrestamoAna);
        sectorPrestamos.recibirCliente(ana, NivelPrioridad.NORMAL, altaPrestamoAna);
        sectorPrestamos.llamarClienteAMostrador();
        mostradorPrestamos.liberar();

        sucursal.cargarFormulaComision("COM-2026", "sal * 0.10 + pro", mostradorEjecutivos.getId());
        System.out.println("Fórmula vigente: " + sucursal.obtenerFormulaVigenteLimpia());

        ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> liquidacion = sucursal.liquidarComisionesVigentes();
        System.out.println("Liquidación real (queda auditada en el historial):");
        imprimirLiquidacion(liquidacion);

        ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> simulacion = sucursal.simularNuevaFormula("COM-SIM", "sal * 0.20");
        System.out.println("Simulación de una fórmula alternativa (NO queda auditada):");
        imprimirLiquidacion(simulacion);

        System.out.println();
        System.out.println("Auditoría - conteo de interacciones por tipo");
        imprimirConteoInteracciones(sucursal.obtenerConteoInteraccionesPorTipo());

        System.out.println();
        System.out.println("Consultas del Hito 1 ");

        System.out.println("Documentos registrados de María:");
        imprimirDocumentos(sucursal.obtenerDocumentosCliente(maria.getCi()));

        System.out.println("Historial completo de María:");
        imprimirInteracciones(sucursal.obtenerHistorialCliente(maria.getCi()));

        System.out.println("Documentos vencidos de María:");
        imprimirDocumentos(sucursal.obtenerDocumentosVencidosCliente(maria.getCi()));

        System.out.println("Productos vencidos o cancelados de Lucía:");
        imprimirProductos(lucia.obtenerProductosVencidosOCancelados());
    }

    private static Documento crearDocumento(TipoDocumento tipo, Cliente cliente, LocalDate presentacion, LocalDate vigencia) {
        return new Documento("DOC-" + (contadorDocumentos++), tipo, cliente, presentacion, vigencia);
    }

    private static void imprimirArbolProducto(IProducto producto, int nivel) {
        String sangria = "   ".repeat(nivel);
        System.out.println(sangria + "- " + producto.getId() + " (" + producto.getClass().getSimpleName() + ")");

        TDALista<IProducto> hijos = producto.obtenerComponentes();
        for (int i = 0; i < hijos.tamano(); i++) {
            imprimirArbolProducto(hijos.obtener(i), nivel + 1);
        }
    }

    private static void imprimirLiquidacion(ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> resultados) {
        for (int i = 0; i < resultados.tamano(); i++) {
            ServicioLiquidacionComisiones.ResultadoLiquidacion resultado = resultados.obtener(i);
            System.out.println("   - " + resultado.getCiCliente() + " -> comisión: " + resultado.getMontoComision());
        }
    }

    private static void imprimirInteracciones(Pila<Interaccion> pila) {
        for (int i = 0; i < pila.tamano(); i++) {
            Interaccion interaccion = pila.obtener(i);
            System.out.println("   - " + interaccion.getTipo() + " | cliente " + interaccion.getClienteId() + " | mostrador " + interaccion.getMostradorId());
        }
    }

    private static void imprimirDocumentos(Pila<Documento> pila) {
        for (int i = 0; i < pila.tamano(); i++) {
            Documento doc = pila.obtener(i);
            String vigencia = doc.getVigencia() == null ? "sin vencimiento" : doc.getVigencia().toString();
            System.out.println("   - " + doc.getId() + " | " + doc.getTipo() + " | vigencia " + vigencia + " | vigente = " + doc.estaVigente());
        }
    }

    private static void imprimirConteoInteracciones(ListaEnlazada<ConteoInteraccion> conteos) {
        for (int i = 0; i < conteos.tamano(); i++) {
            ConteoInteraccion conteo = conteos.obtener(i);
            System.out.println("   - " + conteo.getTipo() + ": " + conteo.getCantidad());
        }
    }

    private static void imprimirProductos(ListaEnlazada<IProducto> productos) {
        for (int i = 0; i < productos.tamano(); i++) {
            IProducto producto = productos.obtener(i);
            System.out.println("   - " + producto.getId() + " | estado " + producto.getEstado());
        }
    }
}
