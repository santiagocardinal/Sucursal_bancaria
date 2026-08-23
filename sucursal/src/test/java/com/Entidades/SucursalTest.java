package com.Entidades;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class SucursalTest {

    private SolicitudAtencion crearSolicitud() {
        return new SolicitudAtencion(TipoInteraccion.CONSULTA, "T1", 0, new ListaEnlazada<>());
    }

    @Test
    public void testConstructorGuardaId() {
        Sucursal sucursal = new Sucursal("SUC1");

        assertNotNull(sucursal);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorIdNuloLanzaExcepcion() {
        new Sucursal(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarSectorNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.agregarSector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarInteraccionNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.registrarInteraccion(null);
    }

    @Test
    public void testRegistrarInteraccionCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), "M1");
        sucursal.registrarInteraccion(interaccion);

        Pila<Interaccion> resultado = sucursal.obtenerHistorialCliente(cliente.getCi());

        assertEquals(1, resultado.tamano());
        assertEquals(interaccion, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarDocumentoNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.registrarDocumento(null);
    }

    @Test
    public void testRegistrarDocumentoCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

        Documento documento = new Documento("DOC001", TipoDocumento.CONTRATO, cliente, LocalDate.now(), null);
        sucursal.registrarDocumento(documento);

        Pila<Documento> resultado = sucursal.obtenerDocumentosCliente(cliente.getCi());

        assertEquals(1, resultado.tamano());
        assertEquals(documento, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObtenerHistorialClienteInexistenteLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.obtenerHistorialCliente("99999999");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObtenerDocumentosClienteInexistenteLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.obtenerDocumentosCliente("99999999");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorClienteNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);

        sucursal.registrarClienteEnSector(null, sector, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSectorNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, null, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorPrioridadNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, null, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSolicitudNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSectorNoRegistradoLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test
    public void testRegistrarClienteEnSectorCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = crearSolicitud();

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, solicitud);

        assertEquals(0, sector.estimarPosicionEnCola(cliente));
        assertSame(solicitud, cliente.getSolicitudActual());
    }
}