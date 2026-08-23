package com.example.EstrategiasDeAtencion;

import static org.junit.Assert.*;

import org.junit.Test;

import com.Entidades.Cliente;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.Interaccion;
import com.Entidades.Sector;
import com.Entidades.Sucursal;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.EstadoProducto;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionEjecutivosTest {

    private void registrarClienteEnSucursal(Sucursal sucursal, Cliente cliente) {
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        SolicitudAtencion solicitudRegistro = new SolicitudAtencion(TipoInteraccion.CONSULTA, "X", 0, new ListaEnlazada<>());
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, solicitudRegistro);
    }

    @Test
    public void testConsultaConProductoExistenteRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionEjecutivos estrategia = new AtencionEjecutivos(sucursal);
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
    public void testConsultaConProductoInexistenteNoRegistraNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionEjecutivos estrategia = new AtencionEjecutivos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "NOEXISTE", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertTrue(historial.esVacio());
    }

    @Test
    public void testBajaProductoCancelaYRegistraInteraccionYDocumento() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionEjecutivos estrategia = new AtencionEjecutivos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Cuenta cuenta = new Cuenta("CTA001", 1000);
        cliente.agregarProducto(cuenta);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.BAJA_PRODUCTO, "CTA001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(EstadoProducto.CANCELADO, cuenta.getEstado());

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.BAJA_PRODUCTO, historial.tope().getTipo());

        Pila<Documento> documentos = sucursal.obtenerDocumentosCliente(cliente.getCi());
        assertEquals(1, documentos.tamano());
        assertEquals(TipoDocumento.COMPROBANTE_BAJA, documentos.tope().getTipo());
    }

    

    @Test
    public void testModificacionActivaProductoYRegistraInteraccion() {
        Sucursal sucursal = new Sucursal("SUC1");
        AtencionEjecutivos estrategia = new AtencionEjecutivos(sucursal);
        Cliente cliente = new Cliente("12345678");
        registrarClienteEnSucursal(sucursal, cliente);
        Cuenta cuenta = new Cuenta("CTA001", 1000);
        cuenta.modificarEstado(EstadoProducto.VENCIDO);
        cliente.agregarProducto(cuenta);

        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.MODIFICACION, "CTA001", 0, new ListaEnlazada<>());

        estrategia.atender(cliente, solicitud, "M1");

        assertEquals(EstadoProducto.ACTIVO, cuenta.getEstado());

        Pila<Interaccion> historial = sucursal.obtenerHistorialCliente(cliente.getCi());
        assertEquals(1, historial.tamano());
        assertEquals(TipoInteraccion.MODIFICACION, historial.tope().getTipo());
    }

   

}