package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Enums.TipoInteraccion;

public class ConteoInteraccionTest {

    @Test
    public void testConstructorGuardaTipoYCantidadInicialEsUno() {
        ConteoInteraccion conteo = new ConteoInteraccion(TipoInteraccion.CONSULTA);

        assertEquals(TipoInteraccion.CONSULTA, conteo.getTipo());
        assertEquals(1, conteo.getCantidad());
    }

    @Test
    public void testIncrementarAumentaCantidad() {
        ConteoInteraccion conteo = new ConteoInteraccion(TipoInteraccion.CONSULTA);

        conteo.incrementar();

        assertEquals(2, conteo.getCantidad());
    }

    @Test
    public void testIncrementarVariasVeces() {
        ConteoInteraccion conteo = new ConteoInteraccion(TipoInteraccion.CONSULTA);

        conteo.incrementar();
        conteo.incrementar();
        conteo.incrementar();

        assertEquals(4, conteo.getCantidad());
    }
}