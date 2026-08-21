package com.example.Caja_De_Herramientas.Lista;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaCircular;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaCircularTest {

    @Test
    public void testListaNuevaEstaVacia() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarUnElemento() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosElementos() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(null);
    }

    @Test
    public void testAgregarIndiceCeroListaVacia() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(0, 10);

        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarAlInicio() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(20);
        lista.agregar(30);

        lista.agregar(0, 10);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarEnMedio() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.agregar(2, 30);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativo() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamano() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        lista.agregar(2, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullPorIndice() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(0, null);
    }

    @Test
    public void testObtener() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceFueraDeRango() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        lista.obtener(1);
    }

    @Test
    public void testRemoverPrimero() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
    public void testRemoverElementoIntermedio() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer eliminado = lista.remover(2);

        assertEquals(Integer.valueOf(30), eliminado);
        assertEquals(2, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
    }

    @Test
    public void testRemoverUnicoElemento() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        assertEquals(Integer.valueOf(10), lista.remover(0));

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        lista.remover(1);
    }

    @Test
    public void testRemoverPorElemento() {
        ListaCircular<String> lista = new ListaCircular<>();

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
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertFalse(lista.remover("B"));
        assertEquals(1, lista.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertFalse(lista.remover((String) null));
    }

    @Test
    public void testRemoverDuplicadoEliminaPrimeraAparicion() {
        ListaCircular<String> lista = new ListaCircular<>();

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
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertFalse(lista.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(0, lista.indiceDe("A"));
        assertEquals(1, lista.indiceDe("B"));
        assertEquals(2, lista.indiceDe("C"));
    }

    @Test
    public void testIndiceDeInexistente() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeNull() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe(null));
    }

    @Test
    public void testBuscar() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(5);
        lista.agregar(10);
        lista.agregar(20);

        Integer resultado = lista.buscar(numero -> numero > 8);

        assertEquals(Integer.valueOf(10), resultado);
    }

    @Test
    public void testBuscarDevuelvePrimerElementoQueCumple() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(5);
        lista.agregar(15);
        lista.agregar(20);

        assertEquals(Integer.valueOf(15), lista.buscar(numero -> numero >= 15));
    }

    @Test
    public void testBuscarSinResultado() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(20);

        assertNull(lista.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarCriterioNull() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.buscar(null);
    }

    @Test
    public void testOrdenarAscendente() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(30);
        lista.agregar(20);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.reverseOrder());

        assertEquals(Integer.valueOf(30), ordenada.obtener(0));
        assertEquals(Integer.valueOf(20), ordenada.obtener(1));
        assertEquals(Integer.valueOf(10), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarNoModificaOriginal() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(20);
        lista.agregar(10);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertNotSame(lista, ordenada);
    }

    @Test
    public void testOrdenarListaVacia() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertTrue(ordenada.esVacio());
        assertEquals(0, ordenada.tamano());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarComparatorNull() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);

        lista.ordenar(null);
    }

    @Test
    public void testTamanoSeActualizaCorrectamente() {
        ListaCircular<Integer> lista = new ListaCircular<>();

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
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarDespuesDeVaciar() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.vaciar();

        lista.agregar(30);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(30), lista.obtener(0));
    }
}