package com.example.Caja_de_Herramientas.Pila;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Pila.Pila;

public class PilaTest {

    @Test
    public void testPilaNuevaEstaVacia() {
        Pila<Integer> pila = new Pila<>();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testMeteElemento() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);

        assertFalse(pila.esVacio());
        assertEquals(1, pila.tamano());
        assertEquals(Integer.valueOf(10), pila.tope());
    }

    @Test
    public void testTopeDevuelveUltimoElementoAgregado() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);
        pila.mete(20);
        pila.mete(30);

        assertEquals(Integer.valueOf(30), pila.tope());
    }

    @Test
    public void testTopeNoEliminaElemento() {
        Pila<String> pila = new Pila<>();

        pila.mete("A");

        assertEquals("A", pila.tope());
        assertEquals("A", pila.tope());
        assertEquals(1, pila.tamano());
    }

    @Test
    public void testSacaElemento() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);
        pila.mete(20);

        Integer resultado = pila.saca();

        assertEquals(Integer.valueOf(20), resultado);
        assertEquals(1, pila.tamano());
    }

    @Test
    public void testOrdenLIFO() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);
        pila.mete(20);
        pila.mete(30);

        assertEquals(Integer.valueOf(30), pila.saca());
        assertEquals(Integer.valueOf(20), pila.saca());
        assertEquals(Integer.valueOf(10), pila.saca());
    }

    @Test
    public void testPilaQuedaVaciaDespuesDeSacarTodo() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);
        pila.mete(20);

        pila.saca();
        pila.saca();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSacaPilaVaciaLanzaExcepcion() {
        Pila<Integer> pila = new Pila<>();

        pila.saca();
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopePilaVaciaLanzaExcepcion() {
        Pila<Integer> pila = new Pila<>();

        pila.tope();
    }

    @Test
    public void testVaciar() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(10);
        pila.mete(20);
        pila.mete(30);

        pila.vaciar();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testContieneHeredadoDeListaEnlazada() {
        Pila<String> pila = new Pila<>();

        pila.mete("A");
        pila.mete("B");

        assertTrue(pila.contiene("A"));
        assertTrue(pila.contiene("B"));
        assertFalse(pila.contiene("C"));
    }

    @Test
    public void testIndiceDeHeredadoDeListaEnlazada() {
        Pila<String> pila = new Pila<>();

        pila.mete("A");
        pila.mete("B");
        pila.mete("C");

        // La pila queda:
        // C -> B -> A

        assertEquals(0, pila.indiceDe("C"));
        assertEquals(1, pila.indiceDe("B"));
        assertEquals(2, pila.indiceDe("A"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeteNullLanzaExcepcion() {
        Pila<Integer> pila = new Pila<>();

        pila.mete(null);
    }
}