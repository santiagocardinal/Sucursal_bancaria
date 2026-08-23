package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.IEstrategiaAtencion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class SectorTest {

    private static class EstrategiaAtencionFalsa implements IEstrategiaAtencion {
        private boolean fueLlamada = false;

        @Override
        public void atender(Cliente cliente, SolicitudAtencion solicitudAtencion, String mostradorId) {
            this.fueLlamada = true;
        }
    }

    private SolicitudAtencion crearSolicitud() {
        return new SolicitudAtencion(TipoInteraccion.CONSULTA, "T1", 0, new ListaEnlazada<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorSucursalNulaLanzaExcepcion() {
        new Sector(null);
    }

    @Test
    public void testContadorTurnosInicialEsUno() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        assertEquals(1, sector.getContadorTurnos());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarMostradorNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        sector.agregarMostrador(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarMostradorDeOtroSectorLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector1 = new Sector(sucursal);
        Sector sector2 = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector2, null, true);

        sector1.agregarMostrador(mostrador);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecibirClienteNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        sector.recibirCliente(null, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecibirClientePrioridadNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");

        sector.recibirCliente(cliente, null, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRecibirClienteSolicitudNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");

        sector.recibirCliente(cliente, NivelPrioridad.NORMAL, null);
    }

    @Test
    public void testRecibirClienteAsignaTurnoYAumentaContador() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = crearSolicitud();

        sector.recibirCliente(cliente, NivelPrioridad.NORMAL, solicitud);

        assertEquals(1, cliente.getNumeroTurno());
        assertEquals(2, sector.getContadorTurnos());
    }

    @Test
    public void testRecibirClienteGuardaSolicitudEnCliente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = crearSolicitud();

        sector.recibirCliente(cliente, NivelPrioridad.NORMAL, solicitud);

        assertSame(solicitud, cliente.getSolicitudActual());
    }

    @Test
    public void testLlamarClienteAMostradorSinClientesDevuelveFalse() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector, null, true);
        sector.agregarMostrador(mostrador);

        assertFalse(sector.llamarClienteAMostrador());
    }

    @Test
    public void testLlamarClienteAMostradorSinMostradoresLibresDevuelveFalse() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Mostrador mostrador = new Mostrador("M1", sector, null, false);
        sector.agregarMostrador(mostrador);

        Cliente cliente = new Cliente("12345678");
        sector.recibirCliente(cliente, NivelPrioridad.NORMAL, crearSolicitud());

        assertFalse(sector.llamarClienteAMostrador());
    }

    @Test
    public void testLlamarClienteAMostradorAtiendeCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        EstrategiaAtencionFalsa estrategia = new EstrategiaAtencionFalsa();
        Mostrador mostrador = new Mostrador("M1", sector, estrategia, true);
        sector.agregarMostrador(mostrador);

        Cliente cliente = new Cliente("12345678");
        sector.recibirCliente(cliente, NivelPrioridad.NORMAL, crearSolicitud());

        boolean resultado = sector.llamarClienteAMostrador();

        assertTrue(resultado);
        assertFalse(mostrador.estaLibre());
        assertTrue(estrategia.fueLlamada);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEstimarPosicionEnColaClienteNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        sector.estimarPosicionEnCola(null);
    }

    @Test
    public void testEstimarPosicionEnCola() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);

        Cliente cliente1 = new Cliente("11111111");
        Cliente cliente2 = new Cliente("22222222");
        Cliente cliente3 = new Cliente("33333333");

        sector.recibirCliente(cliente1, NivelPrioridad.NORMAL, crearSolicitud());
        sector.recibirCliente(cliente2, NivelPrioridad.NORMAL, crearSolicitud());
        sector.recibirCliente(cliente3, NivelPrioridad.NORMAL, crearSolicitud());

        assertEquals(1, sector.estimarPosicionEnCola(cliente2));
    }
}