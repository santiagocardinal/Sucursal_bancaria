package com.example.Caja_De_Herramientas.Arboles;

import com.example.Caja_de_Herramientas.Arboles.ABBImpl;
import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Caja_de_Herramientas.Arboles.TDAElemento;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ABBImplTest {

    // Estructura vacia
    @Test
    public void arbolNuevoEstaVacio() {
        ABBImpl<Integer> arbol = new ABBImpl<>();

        assertTrue(arbol.esVacio());
        assertEquals(0, arbol.cantidadNodos());
        assertEquals(0, arbol.cantidadHojas());
        assertEquals(0, arbol.cantidadNodosInternos());
        assertEquals(0, arbol.altura());
        assertNull(arbol.buscar(4));
        assertFalse(arbol.eliminar(4));
    }

    // Un unico nodo
    @Test
    public void insertarUnSoloElemento() {
        ABBImpl<Integer> arbol = new ABBImpl<>();

        assertTrue(arbol.insertar(7));
        assertFalse(arbol.esVacio());
        assertEquals(1, arbol.cantidadNodos());
        assertEquals(1, arbol.cantidadHojas());
        assertEquals(0, arbol.cantidadNodosInternos());
        assertEquals(Integer.valueOf(7), arbol.buscar(7));
    }

    @Test
    public void eliminarUnicoElemento() {
        ABBImpl<Integer> arbol = new ABBImpl<>();

        arbol.insertar(7);
        assertTrue(arbol.eliminar(7));

        assertTrue(arbol.esVacio());
        assertEquals(0, arbol.cantidadNodos());
        assertNull(arbol.buscar(7));
    }

    // Busquedas
    @Test
    public void buscarElementoExistente() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(10);

        assertEquals(Integer.valueOf(3), arbol.buscar(3));
        assertEquals(Integer.valueOf(10), arbol.buscar(10));
    }

    @Test
    public void buscarElementoInexistente() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(10);

        assertNull(arbol.buscar(14));
        assertFalse(arbol.eliminar(14));
        assertEquals(3, arbol.cantidadNodos());
    }

    // Eliminaciones
    @Test
    public void eliminarNodoHoja() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(10);

        assertTrue(arbol.eliminar(3));
        assertNull(arbol.buscar(3));
        assertEquals(2, arbol.cantidadNodos());
    }

    @Test
    public void eliminarNodoConUnSoloHijo() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(6);
        arbol.insertar(9);
        arbol.insertar(12); // 9 solo tiene como hijo derecho al 12

        assertTrue(arbol.eliminar(9));
        assertNull(arbol.buscar(9));
        assertEquals(Integer.valueOf(12), arbol.buscar(12));
        assertEquals(2, arbol.cantidadNodos());
    }

    @Test
    public void eliminarNodoConDosHijos() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(4);
        arbol.insertar(12);
        arbol.insertar(2);
        arbol.insertar(5);

        // 4 tiene como hijos al 2 y al 5
        assertTrue(arbol.eliminar(4));
        assertNull(arbol.buscar(4));
        assertEquals(Integer.valueOf(2), arbol.buscar(2));
        assertEquals(Integer.valueOf(5), arbol.buscar(5));
        assertEquals(4, arbol.cantidadNodos());
    }

    // Recorridos
    @Test
    public void recorridoInorden() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(11);
        arbol.insertar(5);

        List<Integer> lista = new ArrayList<>();
        arbol.inOrder(x -> lista.add(x));

        assertEquals(4, lista.size());
        assertEquals(Integer.valueOf(3), lista.get(0));
        assertEquals(Integer.valueOf(5), lista.get(1));
        assertEquals(Integer.valueOf(8), lista.get(2));
        assertEquals(Integer.valueOf(11), lista.get(3));
    }

    @Test
    public void recorridoPreorden() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(11);

        List<Integer> lista = new ArrayList<>();
        arbol.preOrder(x -> lista.add(x));

        assertEquals(Integer.valueOf(8), lista.get(0)); // Raiz primero
        assertEquals(Integer.valueOf(3), lista.get(1));
        assertEquals(Integer.valueOf(11), lista.get(2));
    }

    @Test
    public void recorridoPostorden() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(11);

        List<Integer> lista = new ArrayList<>();
        arbol.postOrder(x -> lista.add(x));

        assertEquals(Integer.valueOf(3), lista.get(0));
        assertEquals(Integer.valueOf(11), lista.get(1));
        assertEquals(Integer.valueOf(8), lista.get(2)); // Raiz al final
    }

    @Test
    public void recorridoPorNivel() {
        ABBImpl<Integer> arbol = new ABBImpl<>();
        arbol.insertar(8);
        arbol.insertar(3);
        arbol.insertar(11);

        // Nivel 0 es la raiz (8), Nivel 1 son sus hijos (3 y 11)
        TDALista<TDAElemento<Integer>> nivelCero = arbol.enNivel(0);
        assertEquals(1, nivelCero.tamano());
        assertEquals(Integer.valueOf(8), nivelCero.obtener(0).getDato());

        TDALista<TDAElemento<Integer>> nivelUno = arbol.enNivel(1);
        assertEquals(2, nivelUno.tamano());
    }

    // Arbol degenerado
    @Test
    public void arbolDegeneradoPorInsercionOrdenada() {
        ABBImpl<Integer> arbol = new ABBImpl<>();

        for (int i = 1; i <= 5; i++) {
            arbol.insertar(i);
        }

        assertEquals(5, arbol.cantidadNodos());
        assertEquals(5, arbol.altura()); // Degenera en lista con altura igual a la cantidad de nodos
    }
}