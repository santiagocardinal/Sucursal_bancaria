package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Enums.TipoInteraccion;

public class InteraccionTest {

    @Test
    public void testConstructorGuardaTipoClienteIdYMostradorId() {
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1");

        assertEquals(TipoInteraccion.CONSULTA, interaccion.getTipo());
        assertEquals("C1", interaccion.getClienteId());
        assertEquals("M1", interaccion.getMostradorId());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorTipoNuloLanzaExcepcion() {
        new Interaccion(null, "C1", "M1");
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorClienteIdNuloLanzaExcepcion() {
        new Interaccion(TipoInteraccion.CONSULTA, null, "M1");
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorMostradorIdNuloLanzaExcepcion() {
        new Interaccion(TipoInteraccion.CONSULTA, "C1", null);
    }
}