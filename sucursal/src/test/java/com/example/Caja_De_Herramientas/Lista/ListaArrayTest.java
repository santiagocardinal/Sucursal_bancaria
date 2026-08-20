package com.example.Caja_De_Herramientas.Lista;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaArrayTest {

    @Test
    public void testListaNuevaEstaVacia() {
        ListaArray<Integer> lista = new ListaArray<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarUnElemento() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosElementos() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullLanzaExcepcion() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(null);
    }

    @Test
    public void testAumentaCapacidadCuandoSeLlena() {
        ListaArray<Integer> lista = new ListaArray<>();

        for (int i = 0; i < 15; i++) {
            lista.agregar(i);
        }

        assertEquals(15, lista.tamano());

        for (int i = 0; i < 15; i++) {
            assertEquals(Integer.valueOf(i), lista.obtener(i));
        }
    }

    @Test
    public void testAumentaCapacidadVariasVeces() {
        ListaArray<Integer> lista = new ListaArray<>();

        for (int i = 0; i < 45; i++) {
            lista.agregar(i);
        }

        assertEquals(45, lista.tamano());

        assertEquals(Integer.valueOf(0), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(20));
        assertEquals(Integer.valueOf(44), lista.obtener(44));
    }

    @Test
    public void testAgregarEnIndiceCero() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(20);
        lista.agregar(30);

        lista.agregar(0, 10);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceIntermedio() {
        ListaArray<Integer> lista = new ListaArray<>();

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
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.agregar(2, 30);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarPorIndiceCuandoArrayEstaLleno() {
        ListaArray<Integer> lista = new ListaArray<>();

        for (int i = 0; i < 10; i++) {
            lista.agregar(i);
        }

        lista.agregar(5, 100);

        assertEquals(11, lista.tamano());

        assertEquals(Integer.valueOf(4), lista.obtener(4));
        assertEquals(Integer.valueOf(100), lista.obtener(5));
        assertEquals(Integer.valueOf(5), lista.obtener(6));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativo() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamano() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);

        lista.agregar(2, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullPorIndice() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(0, null);
    }

    @Test
    public void testObtener() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceFueraDeRango() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);

        lista.obtener(1);
    }

    @Test
    public void testRemoverPrimero() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(0);

        assertEquals(Integer.valueOf(10), eliminado);
        assertEquals(2, lista.tamano());

        assertEquals(Integer.valueOf(20), lista.obtener(0));
        assertEquals(Integer.valueOf(30), lista.obtener(1));
    }

    @Test
    public void testRemoverIntermedio() {
        ListaArray<Integer> lista = new ListaArray<>();

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
    public void testRemoverUltimo() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(2);

        assertEquals(Integer.valueOf(30), eliminado);
        assertEquals(2, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);

        lista.remover(1);
    }

    @Test
    public void testRemoverPorElemento() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertTrue(lista.remover("B"));

        assertEquals(2, lista.tamano());
        assertFalse(lista.contiene("B"));

        assertEquals("A", lista.obtener(0));
        assertEquals("C", lista.obtener(1));
    }

    @Test
    public void testRemoverElementoInexistente() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertFalse(lista.remover("B"));
        assertEquals(1, lista.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertFalse(lista.remover((String) null));
    }

    @Test
    public void testRemoverDuplicadoEliminaPrimeraAparicion() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("A");

        assertTrue(lista.remover("A"));

        assertEquals(2, lista.tamano());
        assertEquals("B", lista.obtener(0));
        assertEquals("A", lista.obtener(1));
    }

    @Test
    public void testContiene() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertFalse(lista.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(0, lista.indiceDe("A"));
        assertEquals(1, lista.indiceDe("B"));
        assertEquals(2, lista.indiceDe("C"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeNull() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe(null));
    }

    @Test
    public void testBuscar() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(5);
        lista.agregar(10);
        lista.agregar(20);

        Integer resultado = lista.buscar(numero -> numero > 8);

        assertEquals(Integer.valueOf(10), resultado);
    }

    @Test
    public void testBuscarDevuelvePrimerElementoQueCumple() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(5);
        lista.agregar(15);
        lista.agregar(20);

        assertEquals(Integer.valueOf(15), lista.buscar(numero -> numero >= 15));
    }

    @Test
    public void testBuscarSinResultado() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);

        assertNull(lista.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarCriterioNull() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.buscar(null);
    }

    @Test
    public void testOrdenarAscendente() {
        ListaArray<Integer> lista = new ListaArray<>();

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
        ListaArray<Integer> lista = new ListaArray<>();

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
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        lista.ordenar(Comparator.naturalOrder());

        assertEquals(Integer.valueOf(30), lista.obtener(0));
        assertEquals(Integer.valueOf(10), lista.obtener(1));
        assertEquals(Integer.valueOf(20), lista.obtener(2));
    }

    @Test
    public void testOrdenarDevuelveListaDistinta() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(20);
        lista.agregar(10);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertNotSame(lista, ordenada);
    }

    @Test
    public void testOrdenarListaVacia() {
        ListaArray<Integer> lista = new ListaArray<>();

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertTrue(ordenada.esVacio());
        assertEquals(0, ordenada.tamano());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarComparatorNull() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);

        lista.ordenar(null);
    }

    @Test
    public void testTamanoSeActualizaCorrectamente() {
        ListaArray<Integer> lista = new ListaArray<>();

        assertEquals(0, lista.tamano());

        lista.agregar(10);
        assertEquals(1, lista.tamano());

        lista.agregar(20);
        assertEquals(2, lista.tamano());

        lista.remover(0);
        assertEquals(1, lista.tamano());

        lista.remover(0);
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testVaciar() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarDespuesDeVaciar() {
        ListaArray<Integer> lista = new ListaArray<>();

        for (int i = 0; i < 15; i++) {
            lista.agregar(i);
        }

        lista.vaciar();

        lista.agregar(100);

        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(100), lista.obtener(0));
    }

    @Test
    public void testRemoverYVolverAAgregar() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.remover(1);

        lista.agregar(40);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(30), lista.obtener(1));
        assertEquals(Integer.valueOf(40), lista.obtener(2));
    }
}