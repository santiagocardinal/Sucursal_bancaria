package com.example.Caja_de_Herramientas.Lista;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.NodoDoble;

public class NodoDobleTest {

    @Test
    public void testConstructorGuardaDato() {
        NodoDoble<String> nodo = new NodoDoble<>("A");

        assertEquals("A", nodo.getDato());
    }

    @Test
    public void testConstructorInicializaAnteriorEnNull() {
        NodoDoble<Integer> nodo = new NodoDoble<>(10);

        assertNull(nodo.getAnterior());
    }

    @Test
    public void testConstructorInicializaSiguienteEnNull() {
        NodoDoble<Integer> nodo = new NodoDoble<>(10);

        assertNull(nodo.getSiguiente());
    }

    @Test
    public void testSetAnterior() {
        NodoDoble<Integer> nodo1 = new NodoDoble<>(10);
        NodoDoble<Integer> nodo2 = new NodoDoble<>(20);

        nodo2.setAnterior(nodo1);

        assertSame(nodo1, nodo2.getAnterior());
    }

    @Test
    public void testSetSiguienteHeredado() {
        NodoDoble<Integer> nodo1 = new NodoDoble<>(10);
        NodoDoble<Integer> nodo2 = new NodoDoble<>(20);

        nodo1.setSiguiente(nodo2);

        assertSame(nodo2, nodo1.getSiguiente());
    }

    @Test
    public void testEnlaceDobleEntreNodos() {
        NodoDoble<Integer> nodo1 = new NodoDoble<>(10);
        NodoDoble<Integer> nodo2 = new NodoDoble<>(20);

        nodo1.setSiguiente(nodo2);
        nodo2.setAnterior(nodo1);

        assertSame(nodo2, nodo1.getSiguiente());
        assertSame(nodo1, nodo2.getAnterior());
    }

    @Test
    public void testDatoDelAnterior() {
        NodoDoble<String> nodo1 = new NodoDoble<>("A");
        NodoDoble<String> nodo2 = new NodoDoble<>("B");

        nodo2.setAnterior(nodo1);

        assertEquals("A", nodo2.getAnterior().getDato());
    }

    @Test
    public void testDatoDelSiguiente() {
        NodoDoble<String> nodo1 = new NodoDoble<>("A");
        NodoDoble<String> nodo2 = new NodoDoble<>("B");

        nodo1.setSiguiente(nodo2);

        assertEquals("B", nodo1.getSiguiente().getDato());
    }

    @Test
    public void testSetAnteriorNull() {
        NodoDoble<Integer> nodo1 = new NodoDoble<>(10);
        NodoDoble<Integer> nodo2 = new NodoDoble<>(20);

        nodo2.setAnterior(nodo1);
        nodo2.setAnterior(null);

        assertNull(nodo2.getAnterior());
    }

    @Test
    public void testSetDatoHeredado() {
        NodoDoble<String> nodo = new NodoDoble<>("Inicial");

        nodo.setDato("Nuevo");

        assertEquals("Nuevo", nodo.getDato());
    }
}