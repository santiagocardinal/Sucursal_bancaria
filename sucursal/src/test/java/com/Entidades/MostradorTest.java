package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.EstrategiasDeAtencion.IEstrategiaAtencion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;
import com.example.Enums.TipoInteraccion;

public class MostradorTest {

    private static class EstrategiaAtencionFalsa implements IEstrategiaAtencion {
        private boolean fueLlamada = false;
        private Cliente clienteRecibido;
        private SolicitudAtencion solicitudRecibida;
        private String mostradorIdRecibido;

        @Override
        public void atender(Cliente cliente, SolicitudAtencion solicitudAtencion, String mostradorId) {
            this.fueLlamada = true;
            this.clienteRecibido = cliente;
            this.solicitudRecibida = solicitudAtencion;
            this.mostradorIdRecibido = mostradorId;
        }
    }

    private SolicitudAtencion crearSolicitud() {
        return new SolicitudAtencion(TipoInteraccion.CONSULTA, "T1", 0, new ListaEnlazada<>());
    }

    @Test
    public void testConstructorGuardaDatos() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();

        Mostrador mostrador = new Mostrador("M1", sector, estrategia, true);

        assertEquals("M1", mostrador.getId());
        assertSame(sector, mostrador.getSector());
        assertSame(estrategia, mostrador.getEstrategiaAtencion());
        assertTrue(mostrador.estaLibre());
    }

    @Test
    public void testMostradorOcupadoAlCrear() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        Mostrador mostrador = new Mostrador("M1", sector, null, false);

        assertFalse(mostrador.estaLibre());
    }

    @Test
    public void testLiberarPoneMostradorLibre() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector, null, false);

        mostrador.liberar();

        assertTrue(mostrador.estaLibre());
    }

    @Test
    public void testAtenderClienteOcupaMostradorYLlamaEstrategia() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();
        Mostrador mostrador = new Mostrador("M1", sector, estrategia, true);

        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = crearSolicitud();
        cliente.setSolicitudActual(solicitud);

        mostrador.atender(cliente);

        assertFalse(mostrador.estaLibre());
        assertTrue(estrategia.fueLlamada);
        assertSame(cliente, estrategia.clienteRecibido);
        assertSame(solicitud, estrategia.solicitudRecibida);
        assertEquals("M1", estrategia.mostradorIdRecibido);
    }

    @Test
    public void testAtenderClienteNuloNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();
        Mostrador mostrador = new Mostrador("M1", sector, estrategia, true);

        mostrador.atender(null);

        assertTrue(mostrador.estaLibre());
        assertFalse(estrategia.fueLlamada);
    }

    @Test
    public void testAtenderConMostradorOcupadoNoHaceNada() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();
        Mostrador mostrador = new Mostrador("M1", sector, estrategia, false);

        Cliente cliente = new Cliente("12345678");

        mostrador.atender(cliente);

        assertFalse(mostrador.estaLibre());
        assertFalse(estrategia.fueLlamada);
    }

    @Test
    public void testAtenderSinEstrategiaNoLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector, null, true);

        Cliente cliente = new Cliente("12345678");

        mostrador.atender(cliente);

        assertFalse(mostrador.estaLibre());
    }

    @Test
    public void testSetEstrategiaAtencion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector, null, true);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();

        mostrador.setEstrategiaAtencion(estrategia);

        assertSame(estrategia, mostrador.getEstrategiaAtencion());
    }
}