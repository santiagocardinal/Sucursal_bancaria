package com.example.Caja_De_Herramientas.Cola;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Cola.Cola;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ColaTest {

    @Test
    public void testColaNuevaEstaVacia() {
        Cola<Integer> cola = new Cola<>();

        assertTrue(cola.esVacio());
    }

    @Test
    public void testPoneEnCola() {
        Cola<Integer> cola = new Cola<>();

        assertTrue(cola.poneEnCola(10));
        assertFalse(cola.esVacio());
    }

    @Test
    public void testFrenteDevuelvePrimerElemento() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);
        cola.poneEnCola(20);
        cola.poneEnCola(30);

        assertEquals(Integer.valueOf(10), cola.frente());
    }

    @Test
    public void testFrenteNoEliminaElemento() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);

        assertEquals(Integer.valueOf(10), cola.frente());
        assertEquals(Integer.valueOf(10), cola.frente());
    }

    @Test
    public void testQuitaDeCola() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);

        assertEquals(Integer.valueOf(10), cola.quitaDeCola());
        assertTrue(cola.esVacio());
    }

    @Test
    public void testOrdenFIFO() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);
        cola.poneEnCola(20);
        cola.poneEnCola(30);

        assertEquals(Integer.valueOf(10), cola.quitaDeCola());
        assertEquals(Integer.valueOf(20), cola.quitaDeCola());
        assertEquals(Integer.valueOf(30), cola.quitaDeCola());
    }

    @Test
    public void testFrenteCambiaLuegoDeQuitar() {
        Cola<String> cola = new Cola<>();

        cola.poneEnCola("A");
        cola.poneEnCola("B");

        cola.quitaDeCola();

        assertEquals("B", cola.frente());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteColaVaciaLanzaExcepcion() {
        Cola<Integer> cola = new Cola<>();

        cola.frente();
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        Cola<Integer> cola = new Cola<>();

        cola.quitaDeCola();
    }

    @Test
    public void testQuedaVaciaLuegoDeQuitarTodos() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);
        cola.poneEnCola(20);

        cola.quitaDeCola();
        cola.quitaDeCola();

        assertTrue(cola.esVacio());
    }

    @Test
    public void testAnulaDejaColaVacia() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);
        cola.poneEnCola(20);
        cola.poneEnCola(30);

        cola.anula();

        assertTrue(cola.esVacio());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteDespuesDeAnularLanzaExcepcion() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(10);
        cola.anula();

        cola.frente();
    }
}