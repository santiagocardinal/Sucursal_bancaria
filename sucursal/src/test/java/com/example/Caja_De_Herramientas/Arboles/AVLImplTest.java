package com.example.Caja_De_Herramientas.Arboles;

import com.example.Caja_de_Herramientas.Arboles.*;

import static org.junit.Assert.*;
import org.junit.Test;

public class AVLImplTest {

    // Estructura vacia
    @Test
    public void arbolNuevoEstaVacio() {
        AVLImpl<Integer> avl = new AVLImpl<>();

        assertTrue(avl.esVacio());
        assertEquals(0, avl.altura());
        assertNull(avl.buscar(4));
    }

    // Un unico elemento
    @Test
    public void insertarUnSoloElemento() {
        AVLImpl<Integer> avl = new AVLImpl<>();

        assertTrue(avl.insertar(6));
        assertFalse(avl.esVacio());
        assertEquals(1, avl.cantidadNodos());
        assertEquals(Integer.valueOf(6), avl.buscar(6));
    }

    // Rotacion simple a la izquierda
    @Test
    public void rotacionSimpleIzquierda() {
        AVLImpl<Integer> avl = new AVLImpl<>();
        avl.insertar(3);
        avl.insertar(5);
        avl.insertar(8);

        assertEquals(3, avl.cantidadNodos());
        assertEquals(2, avl.altura());
        assertEquals(Integer.valueOf(5), avl.obtenerRaiz().getDato());
    }

    // Rotacion simple a la derecha
    @Test
    public void rotacionSimpleDerecha() {
        AVLImpl<Integer> avl = new AVLImpl<>();
        avl.insertar(8);
        avl.insertar(5);
        avl.insertar(3);

        assertEquals(3, avl.cantidadNodos());
        assertEquals(2, avl.altura());
        assertEquals(Integer.valueOf(5), avl.obtenerRaiz().getDato());
    }

    // Rotacion doble
    @Test
    public void rotacionDoble() {
        AVLImpl<Integer> avl = new AVLImpl<>();
        avl.insertar(9);
        avl.insertar(3);
        avl.insertar(6);

        assertEquals(3, avl.cantidadNodos());
        assertEquals(2, avl.altura());
        assertEquals(Integer.valueOf(6), avl.obtenerRaiz().getDato());
    }

    // Eliminacion con balanceo
    @Test
    public void eliminarElemento() {
        AVLImpl<Integer> avl = new AVLImpl<>();
        avl.insertar(5);
        avl.insertar(2);
        avl.insertar(8);

        assertTrue(avl.eliminar(2));
        assertNull(avl.buscar(2));
        assertEquals(2, avl.cantidadNodos());
    }

    // Comparacion arbol degenerado vs arbol balanceado
    @Test
    public void diferenciaAlturaDegeneradoVsBalanceado() {
        ABBImpl<Integer> abb = new ABBImpl<>();
        AVLImpl<Integer> avl = new AVLImpl<>();

        for (int i = 1; i <= 7; i++) {
            abb.insertar(i);
            avl.insertar(i);
        }

        assertEquals(7, abb.altura());
        assertEquals(3, avl.altura());
    }
}
