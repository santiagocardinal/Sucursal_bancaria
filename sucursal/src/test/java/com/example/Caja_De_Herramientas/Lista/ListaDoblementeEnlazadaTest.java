package com.example.Caja_de_Herramientas.Lista;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaDoblementeEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaDoblementeEnlazadaTest {

    @Test
    public void testListaNuevaEstaVacia() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarUnElemento() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosElementos() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(null);
    }

    @Test
    public void testAgregarEnIndiceCero() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.agregar(2, 30);

        assertEquals(3, lista.tamano());

        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(20), lista.obtener(1));
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceCeroListaVacia() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(0, 10);

        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativo() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamano() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        lista.agregar(5, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullPorIndice() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(0, null);
    }

    @Test
    public void testObtener() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceFueraDeRango() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        lista.obtener(1);
    }

    @Test
    public void testRemoverPrimeroPorIndice() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
    public void testRemoverElementoIntermedioPorIndice() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        Integer eliminado = lista.remover(0);

        assertEquals(Integer.valueOf(10), eliminado);
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        lista.remover(1);
    }

    @Test
    public void testRemoverElementoExistente() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        boolean resultado = lista.remover("B");

        assertTrue(resultado);
        assertEquals(2, lista.tamano());
        assertFalse(lista.contiene("B"));

        assertEquals("A", lista.obtener(0));
        assertEquals("C", lista.obtener(1));
    }

    @Test
    public void testRemoverPrimerElementoPorDato() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.remover("A"));

        assertEquals(1, lista.tamano());
        assertEquals("B", lista.obtener(0));
    }

    @Test
    public void testRemoverUltimoElementoPorDato() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.remover("B"));

        assertEquals(1, lista.tamano());
        assertEquals("A", lista.obtener(0));
    }

    @Test
    public void testRemoverElementoInexistente() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");

        assertFalse(lista.remover("B"));
        assertEquals(1, lista.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");

        assertFalse(lista.remover((String) null));
    }

    @Test
    public void testRemoverDuplicadoEliminaPrimeraAparicion() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");

        assertFalse(lista.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(0, lista.indiceDe("A"));
        assertEquals(1, lista.indiceDe("B"));
        assertEquals(2, lista.indiceDe("C"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeNullDevuelveMenosUno() {
        ListaDoblementeEnlazada<String> lista = new ListaDoblementeEnlazada<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe(null));
    }

    @Test
    public void testBuscar() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(5);
        lista.agregar(10);
        lista.agregar(20);

        Integer resultado = lista.buscar(numero -> numero > 8);

        assertEquals(Integer.valueOf(10), resultado);
    }

    @Test
    public void testBuscarDevuelvePrimerElementoQueCumple() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(5);
        lista.agregar(15);
        lista.agregar(20);
        lista.agregar(30);

        Integer resultado = lista.buscar(numero -> numero >= 15);

        assertEquals(Integer.valueOf(15), resultado);
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        assertNull(lista.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarCriterioNullLanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.buscar(null);
    }

    @Test
    public void testOrdenarAscendente() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        TDALista<Integer> ordenada =
                lista.ordenar(Comparator.naturalOrder());

        assertEquals(Integer.valueOf(10), ordenada.obtener(0));
        assertEquals(Integer.valueOf(20), ordenada.obtener(1));
        assertEquals(Integer.valueOf(30), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarDescendente() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        lista.ordenar(Comparator.naturalOrder());

        // La lista original debe seguir igual
        assertEquals(Integer.valueOf(30), lista.obtener(0));
        assertEquals(Integer.valueOf(10), lista.obtener(1));
        assertEquals(Integer.valueOf(20), lista.obtener(2));
    }

    @Test
    public void testOrdenarDevuelveListaDistinta() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(20);
        lista.agregar(10);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertNotSame(lista, ordenada);
    }

    @Test
    public void testOrdenarListaVacia() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertTrue(ordenada.esVacio());
        assertEquals(0, ordenada.tamano());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarComparatorNullLanzaExcepcion() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        lista.ordenar(null);
    }

    @Test
    public void testTamanoSeActualizaAlAgregarYRemover() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

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
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testPuedeVolverAAgregarDespuesDeVaciar() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.vaciar();

        lista.agregar(30);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(30), lista.obtener(0));
    }

    @Test
    public void testPuedeAgregarDespuesDeEliminarUltimo() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.remover(1);

        lista.agregar(30);

        assertEquals(2, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
        assertEquals(Integer.valueOf(30), lista.obtener(1));
    }

    @Test
    public void testPuedeAgregarDespuesDeEliminarTodos() {
        ListaDoblementeEnlazada<Integer> lista = new ListaDoblementeEnlazada<>();

        lista.agregar(10);

        lista.remover(0);

        lista.agregar(20);

        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(20), lista.obtener(0));
    }
}