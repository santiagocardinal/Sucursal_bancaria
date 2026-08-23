package com.example;

import java.time.LocalDate;

import com.Entidades.Cliente;
import com.Entidades.ConteoInteraccion;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Interaccion;
import com.Entidades.Mostrador;
import com.Entidades.Sector;
import com.Entidades.Sucursal;
import com.Entidades.TarjetaDeCredito;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
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

        titulo("SUCURSAL BANCARIA - Demostración del sistema");

        // Sucursal, sectores y mostradores
        Sucursal sucursal = new Sucursal("SUC-CENTRO");
        Sector sectorCuentas = new Sector(sucursal);
        Sector sectorPrestamos = new Sector(sucursal);
        Sector sectorEjecutivos = new Sector(sucursal);
        sucursal.agregarSector(sectorCuentas);
        sucursal.agregarSector(sectorPrestamos);
        sucursal.agregarSector(sectorEjecutivos);

        Mostrador mostradorCuentas = new Mostrador("MOST-CTA-01", sectorCuentas, new AtencionCuentasPersonales(sucursal), true);
        Mostrador mostradorPrestamos = new Mostrador("MOST-PRE-01", sectorPrestamos, new AtencionPrestamos(sucursal), true);
        Mostrador mostradorEjecutivos = new Mostrador("MOST-EJE-01", sectorEjecutivos, new AtencionEjecutivos(sucursal), true);
        sectorCuentas.agregarMostrador(mostradorCuentas);
        sectorPrestamos.agregarMostrador(mostradorPrestamos);
        sectorEjecutivos.agregarMostrador(mostradorEjecutivos);
        System.out.println("Sucursal creada con 3 sectores: Cuentas Personales, Préstamos y Ejecutivos.");

        // Clientes
        Cliente maria = new Cliente("20123456");
        Cliente carlos = new Cliente("30234567");
        Cliente lucia = new Cliente("40345678");
        Cliente pedro = new Cliente("50456789");
        Cliente ana = new Cliente("60567890");

        // Productos preexistentes
        Cuenta cuentaLucia = new Cuenta("CTA-" + lucia.getCi(), 50000);
        lucia.agregarProducto(cuentaLucia);
        TarjetaDeCredito tarjetaPedro = new TarjetaDeCredito("TC-" + pedro.getCi());
        pedro.agregarProducto(tarjetaPedro);
        System.out.println("Productos preexistentes: cuenta de Lucía y tarjeta de Pedro.");

        titulo("INGRESO A SALA DE ESPERA");

        // Documentación para trámites de alta
        ListaEnlazada<Documento> docsMaria = new ListaEnlazada<>();
        docsMaria.agregar(crearDocumento(TipoDocumento.CEDULA_IDENTIDAD, maria, LocalDate.now(), null));
        docsMaria.agregar(crearDocumento(TipoDocumento.COMPROBANTE_DOMICILIO, maria, LocalDate.now().minusDays(90), LocalDate.now().minusDays(10))); // vencido a propósito

        ListaEnlazada<Documento> docsCarlos = new ListaEnlazada<>();
        docsCarlos.agregar(crearDocumento(TipoDocumento.COMPROBANTE_INGRESOS, carlos, LocalDate.now(), LocalDate.now().plusMonths(6)));
        docsCarlos.agregar(crearDocumento(TipoDocumento.GARANTIA, carlos, LocalDate.now(), null));

        SolicitudAtencion solMaria = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, null, 15000, docsMaria);
        SolicitudAtencion solLucia = new SolicitudAtencion(TipoInteraccion.CONSULTA, cuentaLucia.getId(), 0, new ListaEnlazada<>());
        SolicitudAtencion solAna = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());
        SolicitudAtencion solCarlosAlta = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, null, 100000, docsCarlos);

        ingresarASala(sucursal, sectorCuentas, maria, NivelPrioridad.NORMAL, solMaria, "María (alta de cuenta)");
        ingresarASala(sucursal, sectorCuentas, lucia, NivelPrioridad.URGENTE, solLucia, "Lucía (consulta urgente)");
        ingresarASala(sucursal, sectorCuentas, ana, NivelPrioridad.NORMAL, solAna, "Ana (consulta, queda pendiente)");
        ingresarASala(sucursal, sectorPrestamos, carlos, NivelPrioridad.TURNO_PREVIO, solCarlosAlta, "Carlos (alta de préstamo)");

        System.out.println("Lucía ingresó última pero con URGENTE: posición " + sectorCuentas.estimarPosicionEnCola(lucia) + " (adelante de María).");

        // Atención en mostrador (Ana queda pendiente a propósito, para la consulta [2])
        titulo("ATENCIÓN EN MOSTRADOR");
        atenderSiguiente(sectorCuentas, mostradorCuentas, 1); // Lucía, por URGENTE
        atenderSiguiente(sectorCuentas, mostradorCuentas, 2); // María
        atenderSiguiente(sectorPrestamos, mostradorPrestamos, 1); // Carlos

        // Segunda ronda: Carlos vuelve por una modificación, Pedro da de baja su tarjeta
        IProducto prestamoCarlos = carlos.obtenerProductos().obtener(0);
        SolicitudAtencion solCarlosModificacion = new SolicitudAtencion(TipoInteraccion.MODIFICACION, prestamoCarlos.getId(), 0, new ListaEnlazada<>());
        SolicitudAtencion solPedroBaja = new SolicitudAtencion(TipoInteraccion.BAJA_PRODUCTO, tarjetaPedro.getId(), 0, new ListaEnlazada<>());

        titulo("SEGUNDA RONDA - SECTOR EJECUTIVOS");
        ingresarASala(sucursal, sectorEjecutivos, pedro, NivelPrioridad.NORMAL, solPedroBaja, "Pedro (baja de tarjeta)");
        ingresarASala(sucursal, sectorEjecutivos, carlos, NivelPrioridad.URGENTE, solCarlosModificacion, "Carlos (modificación urgente)");
        atenderSiguiente(sectorEjecutivos, mostradorEjecutivos, 1); // Carlos, por URGENTE
        atenderSiguiente(sectorEjecutivos, mostradorEjecutivos, 2); // Pedro

        // Auditoría: historial y documentación
        titulo("AUDITORÍA");
        Cliente[] clientes = { maria, carlos, lucia, pedro };
        String[] nombres = { "María", "Carlos", "Lucía", "Pedro" };
        for (int i = 0; i < clientes.length; i++) {
            mostrarHistorial(sucursal, clientes[i], nombres[i]);
        }
        mostrarDocumentos(sucursal, maria, "María");
        mostrarDocumentos(sucursal, pedro, "Pedro");

        System.out.println("Tarjeta de Pedro (" + tarjetaPedro.getId() + ") -> estado: " + tarjetaPedro.getEstado());
        System.out.println("Préstamo de Carlos (" + prestamoCarlos.getId() + ") -> estado: " + prestamoCarlos.getEstado());

        // Consultas 
        titulo("CONSULTAS SOBRE LA INFORMACIÓN ALMACENADA");

        System.out.println("[1] Productos de María:");
        imprimirProductos(maria.obtenerProductos());

        System.out.println("[2] Ana sigue pendiente en Cuentas Personales, posición " + sectorCuentas.estimarPosicionEnCola(ana));

        System.out.println("[3] Historial completo de Carlos (alta + modificación):");
        imprimirInteracciones(sucursal.obtenerHistorialCliente(carlos.getCi()));

        System.out.println("[4] Documentación vencida de María:");
        imprimirDocumentos(sucursal.obtenerDocumentosVencidosCliente(maria.getCi()));

        System.out.println("[5] Productos vencidos o cancelados de Pedro:");
        imprimirProductos(pedro.obtenerProductosVencidosOCancelados());

        System.out.println("[6] Conteo de interacciones por tipo en la sucursal:");
        imprimirConteoInteracciones(sucursal.obtenerConteoInteraccionesPorTipo());

        titulo("FIN DE LA DEMOSTRACIÓN");
    }

    // Para mostrar en pantalla

    private static void titulo(String texto) {
        System.out.println();
        System.out.println("--- " + texto + " ---");
    }

    private static Documento crearDocumento(TipoDocumento tipo, Cliente cliente, LocalDate presentacion, LocalDate vigencia) {
        return new Documento("DOC-" + (contadorDocumentos++), tipo, cliente, presentacion, vigencia);
    }

    private static void ingresarASala(Sucursal sucursal, Sector sector, Cliente cliente, NivelPrioridad prioridad, SolicitudAtencion solicitud, String etiqueta) {
        sucursal.registrarClienteEnSector(cliente, sector, prioridad, solicitud);
        System.out.println(etiqueta + " | turno " + cliente.getNumeroTurno() + " | posición en cola " + sector.estimarPosicionEnCola(cliente));
    }

    private static void atenderSiguiente(Sector sector, Mostrador mostrador, int numeroLlamada) {
        boolean atendido = sector.llamarClienteAMostrador();
        System.out.println(mostrador.getId() + (atendido
            ? " atiende al siguiente cliente (llamada #" + numeroLlamada + ")."
            : ": no hay clientes para atender."));
        mostrador.liberar();
    }

    private static void mostrarHistorial(Sucursal sucursal, Cliente cliente, String etiqueta) {
        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        System.out.println(etiqueta + " registra " + historial.tamano() + " interaccion(es):");
        imprimirInteracciones(historial);
    }

    private static void imprimirInteracciones(Pila<Interaccion> pila) {
        for (int i = 0; i < pila.tamano(); i++) {
            Interaccion interaccion = pila.obtener(i);
            System.out.println("   - " + interaccion.getTipo() + " | cliente " + interaccion.getClienteId() + " | mostrador " + interaccion.getMostradorId());
        }
    }

    private static void mostrarDocumentos(Sucursal sucursal, Cliente cliente, String etiqueta) {
        Pila<Documento> documentos = sucursal.obtenerDocumentosCliente(cliente.getCi());
        System.out.println(etiqueta + " tiene " + documentos.tamano() + " documento(s):");
        imprimirDocumentos(documentos);
    }

    private static void imprimirDocumentos(Pila<Documento> pila) {
        for (int i = 0; i < pila.tamano(); i++) {
            Documento doc = pila.obtener(i);
            String vigencia = doc.getVigencia() == null ? "sin vencimiento" : doc.getVigencia().toString();
            System.out.println("   - " + doc.getId() + " | " + doc.getTipo() + " | vigencia " + vigencia + " | vigente=" + doc.estaVigente());
        }
    }

    private static void imprimirProductos(ListaEnlazada<IProducto> productos) {
        if (productos.esVacio()) {
            System.out.println("   (sin productos)");
            return;
        }
        for (int i = 0; i < productos.tamano(); i++) {
            IProducto producto = productos.obtener(i);
            System.out.println("   - " + producto.getId() + " | " + producto.getClass().getSimpleName() + " | estado " + producto.getEstado());
        }
    }

    private static void imprimirConteoInteracciones(ListaEnlazada<ConteoInteraccion> conteos) {
        for (int i = 0; i < conteos.tamano(); i++) {
            ConteoInteraccion conteo = conteos.obtener(i);
            System.out.println("   - " + conteo.getTipo() + ": " + conteo.getCantidad());
        }
    }
}