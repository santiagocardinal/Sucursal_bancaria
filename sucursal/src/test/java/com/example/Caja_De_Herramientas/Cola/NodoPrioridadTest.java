package com.example.Caja_de_Herramientas.Cola;
import org.junit.Test;

import com.example.Caja_de_Herramientas.Cola.NodoPrioridad;

import static org.junit.Assert.*;

public class NodoPrioridadTest {

    @Test
    public void testConstructorGuardaDato() {
        NodoPrioridad<String> nodo = new NodoPrioridad<>("Tarea", 5);

        assertEquals("Tarea", nodo.getDato());
    }

    @Test
    public void testConstructorGuardaPrioridad() {
        NodoPrioridad<String> nodo = new NodoPrioridad<>("Tarea", 5);

        assertEquals(5, nodo.getPrioridad());
    }

    @Test
    public void testSetPrioridad() {
        NodoPrioridad<Integer> nodo = new NodoPrioridad<>(10, 1);

        nodo.setPrioridad(8);

        assertEquals(8, nodo.getPrioridad());
    }

    @Test
    public void testSetDato() {
        NodoPrioridad<String> nodo = new NodoPrioridad<>("Inicial", 3);

        nodo.setDato("Nuevo");

        assertEquals("Nuevo", nodo.getDato());
    }

    @Test
    public void testSiguienteInicialmenteEsNull() {
        NodoPrioridad<Integer> nodo = new NodoPrioridad<>(10, 2);

        assertNull(nodo.getSiguiente());
    }

    @Test
    public void testPuedeAsignarSiguiente() {
        NodoPrioridad<Integer> nodo1 = new NodoPrioridad<>(10, 1);
        NodoPrioridad<Integer> nodo2 = new NodoPrioridad<>(20, 2);

        nodo1.setSiguiente(nodo2);

        assertSame(nodo2, nodo1.getSiguiente());
    }

    @Test
    public void testPrioridadPuedeSerCero() {
        NodoPrioridad<String> nodo = new NodoPrioridad<>("Tarea", 0);

        assertEquals(0, nodo.getPrioridad());
    }

    @Test
    public void testPrioridadPuedeSerNegativa() {
        NodoPrioridad<String> nodo = new NodoPrioridad<>("Tarea", -5);

        assertEquals(-5, nodo.getPrioridad());
    }
}