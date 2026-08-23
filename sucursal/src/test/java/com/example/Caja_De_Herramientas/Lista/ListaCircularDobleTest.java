package com.example.Caja_de_Herramientas.Lista;

import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaCircularDoble;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaCircularDobleTest {

    @Test
    public void testListaNuevaEstaVacia() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarUnElemento() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(10), lista.obtener(0));
    }

    @Test
    public void testAgregarVariosElementos() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(null);
    }

    @Test
    public void testAgregarEnIndiceCero() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.agregar(2, 30);

        assertEquals(3, lista.tamano());
        assertEquals(Integer.valueOf(30), lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceNegativo() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(-1, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarIndiceMayorAlTamano() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        lista.agregar(2, 20);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullPorIndice() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(0, null);
    }

    @Test
    public void testObtener() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceFueraDeRango() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        lista.obtener(1);
    }

    @Test
    public void testRemoverPrimero() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        Integer eliminado = lista.remover(0);

        assertEquals(Integer.valueOf(10), eliminado);
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        lista.remover(1);
    }

    @Test
    public void testRemoverElementoExistente() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");

        assertFalse(lista.remover("B"));
        assertEquals(1, lista.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");

        assertFalse(lista.remover((String) null));
    }

    @Test
    public void testRemoverDuplicadoEliminaPrimeraAparicion() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testContieneNull() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");

        assertFalse(lista.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(0, lista.indiceDe("A"));
        assertEquals(1, lista.indiceDe("B"));
        assertEquals(2, lista.indiceDe("C"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeNull() {
        ListaCircularDoble<String> lista = new ListaCircularDoble<>();

        lista.agregar("A");

        assertEquals(-1, lista.indiceDe(null));
    }

    @Test
    public void testBuscar() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(5);
        lista.agregar(10);
        lista.agregar(20);

        Integer resultado = lista.buscar(numero -> numero > 8);

        assertEquals(Integer.valueOf(10), resultado);
    }

    @Test
    public void testBuscarDevuelvePrimerElementoQueCumple() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(5);
        lista.agregar(15);
        lista.agregar(20);

        assertEquals(
                Integer.valueOf(15),
                lista.buscar(numero -> numero >= 15)
        );
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);
        lista.agregar(20);

        assertNull(lista.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarCriterioNull() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.buscar(null);
    }

    @Test
    public void testOrdenarAscendente() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(20);
        lista.agregar(10);

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertNotSame(lista, ordenada);
    }

    @Test
    public void testOrdenarListaVacia() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        TDALista<Integer> ordenada = lista.ordenar(Comparator.naturalOrder());

        assertTrue(ordenada.esVacio());
        assertEquals(0, ordenada.tamano());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarComparatorNull() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);

        lista.ordenar(null);
    }

    @Test
    public void testTamanoSeActualizaCorrectamente() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

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
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarDespuesDeVaciar() {
        ListaCircularDoble<Integer> lista = new ListaCircularDoble<>();

        lista.agregar(10);
        lista.agregar(20);

        lista.vaciar();

        lista.agregar(30);

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals(Integer.valueOf(30), lista.obtener(0));
    }
}