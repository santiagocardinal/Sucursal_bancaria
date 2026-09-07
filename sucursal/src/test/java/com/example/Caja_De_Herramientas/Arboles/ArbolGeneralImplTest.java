package com.example.Caja_De_Herramientas.Arboles;

import static org.junit.Assert.*;
import org.junit.Test;
import com.example.Caja_de_Herramientas.Arboles.*;

public class ArbolGeneralImplTest {

    // Estructura vacia
    @Test
    public void arbolNuevoEstaVacio() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        assertTrue(arbol.esVacio());
        assertEquals(0, arbol.cantidadNodos());
    }

    // Un unico elemento (raiz)
    @Test
    public void insertarSoloRaiz() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        arbol.insertarRaiz(5);

        assertFalse(arbol.esVacio());
        assertEquals(1, arbol.cantidadNodos());
        assertTrue(arbol.buscar(5));
    }

    // Multiples hijos directos (arbol n-ario)
    @Test
    public void insertarVariosHijosDirectos() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        arbol.insertarRaiz(1);
        assertTrue(arbol.insertarHijo(1, 2));
        assertTrue(arbol.insertarHijo(1, 3));
        assertTrue(arbol.insertarHijo(1, 4));

        assertEquals(4, arbol.cantidadNodos());
        assertTrue(arbol.buscar(3));
    }

    // Varios niveles de profundidad
    @Test
    public void insertarEnVariosNiveles() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        arbol.insertarRaiz(1); // Nivel 0
        assertTrue(arbol.insertarHijo(1, 2)); // Nivel 1: 2 es hijo de 1
        assertTrue(arbol.insertarHijo(2, 6)); // Nivel 2: 6 es hijo de 2

        assertEquals(3, arbol.cantidadNodos());
        assertTrue(arbol.buscar(6));
    }

    // Casos borde: padre inexistente o busqueda fallida
    @Test
    public void insertarHijoConPadreInexistenteDevuelveFalse() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        arbol.insertarRaiz(1);

        // 9 no existe en el arbol, no se puede colgar un hijo de el
        assertFalse(arbol.insertarHijo(9, 4));
        assertEquals(1, arbol.cantidadNodos());
    }

    @Test
    public void buscarElementoInexistente() {
        ArbolGeneralImpl<Integer> arbol = new ArbolGeneralImpl<>();

        arbol.insertarRaiz(1);
        arbol.insertarHijo(1, 2);

        assertFalse(arbol.buscar(12));
    }
}