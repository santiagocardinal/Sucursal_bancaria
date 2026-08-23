package com.example.Caja_de_Herramientas.Lista;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.Nodo;

import static org.junit.Assert.*;

public class NodoTest {

    @Test
    public void testConstructorGuardaDato() {
        Nodo<String> nodo = new Nodo<>("Hola");

        assertEquals("Hola", nodo.getDato());
    }

    @Test
    public void testConstructorInicializaSiguienteEnNull() {
        Nodo<Integer> nodo = new Nodo<>(10);

        assertNull(nodo.getSiguiente());
    }

    @Test
    public void testSetDato() {
        Nodo<String> nodo = new Nodo<>("Inicial");

        nodo.setDato("Nuevo");

        assertEquals("Nuevo", nodo.getDato());
    }

    @Test
    public void testSetSiguiente() {
        Nodo<Integer> nodo1 = new Nodo<>(10);
        Nodo<Integer> nodo2 = new Nodo<>(20);

        nodo1.setSiguiente(nodo2);

        assertSame(nodo2, nodo1.getSiguiente());
    }

    @Test
    public void testDatoDelNodoSiguiente() {
        Nodo<Integer> nodo1 = new Nodo<>(10);
        Nodo<Integer> nodo2 = new Nodo<>(20);

        nodo1.setSiguiente(nodo2);

        assertEquals(Integer.valueOf(20), nodo1.getSiguiente().getDato());
    }

    @Test
    public void testSetSiguienteNull() {
        Nodo<Integer> nodo1 = new Nodo<>(10);
        Nodo<Integer> nodo2 = new Nodo<>(20);

        nodo1.setSiguiente(nodo2);
        nodo1.setSiguiente(null);

        assertNull(nodo1.getSiguiente());
    }
}