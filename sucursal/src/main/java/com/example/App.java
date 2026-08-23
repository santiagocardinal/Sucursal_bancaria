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
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.EstadoProducto;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.AtencionCuentasPersonales;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class App {

    private static int contadorDocumentos = 1;

    public static void main(String[] args) {

        Sucursal sucursal = new Sucursal("SUC-CENTRO");
        Sector sectorCuentas = new Sector(sucursal);
        sucursal.agregarSector(sectorCuentas);

        Mostrador mostradorCuentas = new Mostrador("MOST-CTA-01", sectorCuentas, new AtencionCuentasPersonales(sucursal), true);
        sectorCuentas.agregarMostrador(mostradorCuentas);

        Cliente maria = new Cliente("20123456");
        Cliente lucia = new Cliente("40345678");
        Cliente ana = new Cliente("60567890");

        // Cuenta de Lucía ya VENCIDA, para que la consulta de productos
        // vencidos/cancelados no devuelva una lista vacía.
        Cuenta cuentaLucia = new Cuenta("CTA-" + lucia.getCi(), 50000);
        cuentaLucia.modificarEstado(EstadoProducto.CANCELADO);
        lucia.agregarProducto(cuentaLucia);

        ListaEnlazada<Documento> docsMaria = new ListaEnlazada<>();
        docsMaria.agregar(crearDocumento(TipoDocumento.CEDULA_IDENTIDAD, maria, LocalDate.now(), null));
        // Segundo documento de María, ya vencido (presentado hace 2 años,
        // con vigencia hace 1 año), para que la consulta de documentos
        // vencidos no devuelva una lista vacía.
        docsMaria.agregar(crearDocumento(TipoDocumento.COMPROBANTE_INGRESOS, maria, LocalDate.now().minusYears(2), LocalDate.now().minusYears(1)));

        SolicitudAtencion solMaria = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, null, 15000, docsMaria);
        SolicitudAtencion solLucia = new SolicitudAtencion(TipoInteraccion.CONSULTA, cuentaLucia.getId(), 0, new ListaEnlazada<>());
        SolicitudAtencion solAna = new SolicitudAtencion(TipoInteraccion.CONSULTA, "N/A", 0, new ListaEnlazada<>());

        sucursal.registrarClienteEnSector(maria, sectorCuentas, NivelPrioridad.NORMAL, solMaria);
        sucursal.registrarClienteEnSector(lucia, sectorCuentas, NivelPrioridad.NORMAL, solLucia);
        sucursal.registrarClienteEnSector(ana, sectorCuentas, NivelPrioridad.NORMAL, solAna);

        sectorCuentas.llamarClienteAMostrador();
        mostradorCuentas.liberar();
        sectorCuentas.llamarClienteAMostrador();
        mostradorCuentas.liberar();


        System.out.println("--- CONSULTAS SOBRE LA INFORMACIÓN ALMACENADA ---");

        System.out.println("Documentos registrados de María:");
        imprimirDocumentos(sucursal.obtenerDocumentosCliente(maria.getCi()));

        System.out.println("Historial completo de María:");
        imprimirInteracciones(sucursal.obtenerHistorialCliente(maria.getCi()));

        System.out.println("Ana sigue pendiente, posición en cola: " + sectorCuentas.estimarPosicionEnCola(ana));

        System.out.println("Conteo de interacciones por tipo:");
        imprimirConteoInteracciones(sucursal.obtenerConteoInteraccionesPorTipo());

        System.out.println("Documentos vencidos de María:");
        imprimirDocumentos(sucursal.obtenerDocumentosVencidosCliente(maria.getCi()));

        System.out.println("Productos vencidos o cancelados de Lucía:");
        imprimirProductos(lucia.obtenerProductosVencidosOCancelados());
    }

    private static Documento crearDocumento(TipoDocumento tipo, Cliente cliente, LocalDate presentacion, LocalDate vigencia) {
        return new Documento("DOC-" + (contadorDocumentos++), tipo, cliente, presentacion, vigencia);
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
            System.out.println("   - " + doc.getId() + " | " + doc.getTipo() + " | vigencia " + vigencia + " | vigente=" + doc.estaVigente());
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