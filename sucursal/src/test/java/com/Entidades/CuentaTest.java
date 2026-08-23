package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Enums.EstadoProducto;

public class CuentaTest {

    @Test
    public void testConstructorGuardaIdYSaldoInicial() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        assertEquals("CTA001", cuenta.getId());
        assertEquals(1000, cuenta.getSaldo(), 0.001);
        assertEquals(EstadoProducto.ACTIVO, cuenta.getEstado());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorIdNuloLanzaExcepcion() {
        new Cuenta(null, 1000);
    }

    @Test
    public void testDepositarAumentaSaldo() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        cuenta.depositar(500);

        assertEquals(1500, cuenta.getSaldo(), 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositarMontoCeroLanzaExcepcion() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        cuenta.depositar(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDepositarMontoNegativoLanzaExcepcion() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        cuenta.depositar(-100);
    }

    @Test
    public void testRetirarEfectivoExitoso() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        boolean resultado = cuenta.retirarEfectivo(300);

        assertTrue(resultado);
        assertEquals(700, cuenta.getSaldo(), 0.001);
    }

    @Test
    public void testRetirarEfectivoMayorASaldoDevuelveFalse() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        boolean resultado = cuenta.retirarEfectivo(2000);

        assertFalse(resultado);
        assertEquals(1000, cuenta.getSaldo(), 0.001);
    }

    @Test
    public void testRetirarEfectivoMontoCeroDevuelveFalse() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        boolean resultado = cuenta.retirarEfectivo(0);

        assertFalse(resultado);
        assertEquals(1000, cuenta.getSaldo(), 0.001);
    }

    @Test
    public void testRetirarEfectivoMontoNegativoDevuelveFalse() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        boolean resultado = cuenta.retirarEfectivo(-100);

        assertFalse(resultado);
        assertEquals(1000, cuenta.getSaldo(), 0.001);
    }

    @Test
    public void testModificarEstadoCorrectamente() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        cuenta.modificarEstado(EstadoProducto.VENCIDO);

        assertEquals(EstadoProducto.VENCIDO, cuenta.getEstado());
        assertTrue(cuenta.estaVencido());
    }

    @Test(expected = NullPointerException.class)
    public void testModificarEstadoNuloLanzaExcepcion() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        cuenta.modificarEstado(null);
    }

    @Test
    public void testCuentaActivaNoEstaVencida() {
        Cuenta cuenta = new Cuenta("CTA001", 1000);

        assertFalse(cuenta.estaVencido());
    }
}