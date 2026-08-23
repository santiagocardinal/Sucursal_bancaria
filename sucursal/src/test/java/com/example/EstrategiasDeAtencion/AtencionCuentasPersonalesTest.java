package com.example.EstrategiasDeAtencion;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.Entidades.Cliente;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Interaccion;
import com.Entidades.Sector;
import com.Entidades.Sucursal;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionCuentasPersonalesTest {

    private void registrarClienteEnSucursal(Sucursal sucursal, Cliente cliente) {
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        SolicitudAtencion solicitudRegistro = new SolicitudAtencion(TipoInteraccion.CONSULTA, "X", 0, new ListaEnlazada<>());
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, solicitudRegistro);
    }

    @Test
    public void testConsultaConCuentaExistenteRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        cliente.agregarProducto(new Cuenta("CTA001", 1000));

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "CTA001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.CONSULTA, historial.tope().getTipo());
    }

    @Test
    public void testConsultaConCuentaInexistenteNoRegistraNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "NOEXISTE", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testPagoExitosoRetiraSaldoYRegistraInteraccionYDocumento() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Cuenta cuenta = new Cuenta("CTA001", 1000);
        cliente.agregarProducto(cuenta);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "CTA001", 300, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(700, cuenta.getSaldo(), 0.001);

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.PAGO, historial.tope().getTipo());

        Pila<Documento> documentos = sucursal.obtenerDocumentosCliente(cliente.getCi());
        assertEquals(1, documentos.tamano());
        assertEquals(TipoDocumento.COMPROBANTE_PAGO, documentos.tope().getTipo());
    }

    @Test
    public void testPagoConSaldoInsuficienteNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Cuenta cuenta = new Cuenta("CTA001", 100);
        cliente.agregarProducto(cuenta);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "CTA001", 300, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(100, cuenta.getSaldo(), 0.001);
        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testPagoConCuentaInexistenteNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "NOEXISTE", 300, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testAltaCuentaCreaCuentaYRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "IGNORADO", 5000, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(1, cliente.obtenerProductos().tamano());
        IProducto producto = cliente.obtenerProductos().obtener(0);
        assertTrue(producto instanceof Cuenta);
        Cuenta cuentaCreada = (Cuenta) producto;
        assertEquals(5000, cuentaCreada.getSaldo(), 0.001);

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.ALTA_PRODUCTO, historial.tope().getTipo());
    }

    @Test
    public void testAltaCuentaRegistraDocumentosPresentados() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        ListaEnlazada<Documento> documentos = new ListaEnlazada<>();
        Documento doc1 = new Documento("DOC1", TipoDocumento.CEDULA_IDENTIDAD, cliente, LocalDate.now(), null);
        documentos.agregar(doc1);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "IGNORADO", 5000, documentos);

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Documento> documentosRegistrados = sucursal.obtenerDocumentosCliente(cliente.getCi());
        assertEquals(1, documentosRegistrados.tamano());
        assertEquals(doc1, documentosRegistrados.tope());
    }

    @Test
    public void testModificacionNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionCuentasPersonales estrategia = new AtencionCuentasPersonales(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Cuenta cuenta = new Cuenta("CTA001", 1000);
        cliente.agregarProducto(cuenta);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.MODIFICACION, "CTA001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }
}