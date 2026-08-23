package com.example.Caja_de_Herramientas.Lista;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;

import java.util.Comparator;

import static org.junit.Assert.*;

public class ListaEnlazadaTest {

    @Test
    public void testListaNuevaEstaVacia() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarElemento() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        assertTrue(lista.esVacio());

        lista.agregar(10);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosElementos() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        assertEquals(3, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceCero() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(20);
        lista.agregar(30);

        lista.agregar(0, 10);

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceIntermedio() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(30);

        lista.agregar(1, 20);

        assertEquals(3, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarAlFinalPorIndice() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.agregar(2, 30);

        assertEquals(3, lista.tamano());
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativo() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamano() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);

        lista.agregar(5, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullPorIndice() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(0, null);
    }

    @Test
    public void testObtener() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals("B", lista.obtener(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceInexistente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);

        lista.obtener(1);
    }

    @Test
    public void testRemoverPorIndiceCero() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(0);

        assertEquals(Integer.valueOf(10), eliminado);
        assertEquals(2, lista.tamano());
        assertEquals(Integer.valueOf(20), lista.obtener(0));
    }

    @Test
    public void testRemoverPorIndiceIntermedio() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(1);

        assertEquals(Integer.valueOf(20), eliminado);
        assertEquals(2, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(30), lista.obtener(1));
    }

    @Test
    public void testRemoverUltimoPorIndice() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(2);

        assertEquals(Integer.valueOf(30), eliminado);
        assertEquals(2, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceInexistente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);

        lista.remover(5);
    }

    @Test
    public void testRemoverElementoExistente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        boolean resultado = lista.remover(Integer.valueOf(20));

        assertTrue(resultado);
        assertEquals(2, lista.tamano());
        assertFalse(lista.contiene(20));
    }

    @Test
    public void testRemoverElementoInexistente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        boolean resultado = lista.remover(Integer.valueOf(50));

        assertFalse(resultado);
        assertEquals(2, lista.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);

        assertFalse(lista.remover((Integer) null));
    }

    @Test
    public void testContieneElemento() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testIndiceDe() {
        ListaEnlazada<String> lista = new ListaEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        assertEquals(-1, lista.indiceDe(50));
    }

    @Test
    public void testBuscar() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(5);
        lista.agregar(10);
        lista.agregar(15);

        Integer resultado = lista.buscar(numero -> numero > 8);

        assertEquals(Integer.valueOf(10), resultado);
    }

    @Test
    public void testBuscarSinResultado() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(5);
        lista.agregar(10);

        Integer resultado = lista.buscar(numero -> numero > 100);

        assertNull(resultado);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarCriterioNull() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.buscar(null);
    }

    @Test
    public void testOrdenarAscendente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertEquals(Integer.valueOf(10), ordenada.obtener(0));
        assertEquals(Integer.valueOf(20), ordenada.obtener(1));
        assertEquals(Integer.valueOf(30), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarDescendente() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(30);
        lista.agregar(20);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.reverseOrder());

        assertEquals(Integer.valueOf(30), ordenada.obtener(0));
        assertEquals(Integer.valueOf(20), ordenada.obtener(1));
        assertEquals(Integer.valueOf(10), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarNoModificaListaOriginal() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        lista.ordenar(Comparator.naturalOrder());

        assertEquals(Integer.valueOf(30), lista.obtener(0));
        assertEquals(Integer.valueOf(10), lista.obtener(1));
        assertEquals(Integer.valueOf(20), lista.obtener(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarComparatorNull() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);

        lista.ordenar(null);
    }

    @Test
    public void testTamano() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        assertEquals(0, lista.tamano());

        lista.agregar(10);
        assertEquals(1, lista.tamano());

        lista.agregar(20);
        assertEquals(2, lista.tamano());
    }

    @Test
    public void testVaciar() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testInsertarOrdenadoEnListaVacia() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.insertarOrdenado(10, Comparator.naturalOrder());

        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testInsertarOrdenadoAlPrincipio() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(20);
        lista.agregar(30);

        lista.insertarOrdenado(10, Comparator.naturalOrder());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testInsertarOrdenadoEnMedio() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(30);

        lista.insertarOrdenado(20, Comparator.naturalOrder());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testInsertarOrdenadoAlFinal() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.insertarOrdenado(30, Comparator.naturalOrder());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertarOrdenadoNull() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.insertarOrdenado(null, Comparator.naturalOrder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertarOrdenadoComparatorNull() {
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();

        lista.insertarOrdenado(10, null);
    }
}