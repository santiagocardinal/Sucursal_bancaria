package com.example.EstrategiasDeAtencion;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.Entidades.Cliente;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Interaccion;
import com.Entidades.Prestamo;
import com.Entidades.Sector;
import com.Entidades.Sucursal;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionPrestamosTest {

    private void registrarClienteEnSucursal(Sucursal sucursal, Cliente cliente) {
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        SolicitudAtencion solicitudRegistro = new SolicitudAtencion(TipoInteraccion.CONSULTA, "X", 0, new ListaEnlazada<>());
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, solicitudRegistro);
    }

    @Test
    public void testConsultaConPrestamoExistenteRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        cliente.agregarProducto(new Prestamo("PRE001", 100000, 0.10, 10));

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "PRE001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.CONSULTA, historial.tope().getTipo());
    }

    @Test
    public void testConsultaConPrestamoInexistenteNoRegistraNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "NOEXISTE", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testPagoExitosoRegistraInteraccionYDocumento() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Prestamo prestamo = new Prestamo("PRE001", 100000, 0.10, 10);
        cliente.agregarProducto(prestamo);
        double cuota = prestamo.proximaCuota();

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "PRE001", cuota, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(1, prestamo.getCuotasActual());

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.PAGO, historial.tope().getTipo());

        Pila<Documento> documentos = sucursal.obtenerDocumentosCliente(cliente.getCi());
        assertEquals(1, documentos.tamano());
        assertEquals(TipoDocumento.COMPROBANTE_PAGO, documentos.tope().getTipo());
    }

    @Test
    public void testPagoConMontoIncorrectoNoRegistraNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Prestamo prestamo = new Prestamo("PRE001", 100000, 0.10, 10);
        cliente.agregarProducto(prestamo);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "PRE001", 1, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(0, prestamo.getCuotasActual());
        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testPagoConPrestamoInexistenteNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "NOEXISTE", 100, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testAltaProductoCreaPrestamoYRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "IGNORADO", 50000, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(1, cliente.obtenerProductos().tamano());
        IProducto producto = cliente.obtenerProductos().obtener(0);
        assertTrue(producto instanceof Prestamo);
        Prestamo prestamoCreado = (Prestamo) producto;
        assertEquals(50000, prestamoCreado.getMontoOriginal(), 0.001);
        assertEquals(10, prestamoCreado.getInteres(), 0.001);
        assertEquals(12, prestamoCreado.getCuotasTotales());

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.ALTA_PRODUCTO, historial.tope().getTipo());
    }

    @Test
    public void testAltaProductoRegistraDocumentosPresentados() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        ListaEnlazada<Documento> documentos = new ListaEnlazada<>();
        Documento doc1 = new Documento("DOC1", TipoDocumento.CEDULA_IDENTIDAD, cliente, LocalDate.now(), null);
        documentos.agregar(doc1);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "IGNORADO", 50000, documentos);

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Documento> documentosRegistrados = sucursal.obtenerDocumentosCliente(cliente.getCi());
        assertEquals(1, documentosRegistrados.tamano());
        assertEquals(doc1, documentosRegistrados.tope());
    }

    @Test
    public void testBajaProductoNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionPrestamos estrategia = new AtencionPrestamos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        cliente.agregarProducto(new Prestamo("PRE001", 100000, 0.10, 10));

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.BAJA_PRODUCTO, "PRE001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }
}