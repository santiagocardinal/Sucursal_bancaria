package com.example.Caja_De_Herramientas.Pila;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Pila.PilaPrioridad;

public class PilaPrioridadTest {

    @Test
    public void testPilaNuevaEstaVacia() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testMeteElementoSinPrioridad() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A");

        assertFalse(pila.esVacio());
        assertEquals(1, pila.tamano());
        assertEquals("A", pila.tope());
        assertEquals(0, pila.prioridadTope());
    }

    @Test
    public void testMeteElementoConPrioridad() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 5);

        assertEquals("A", pila.tope());
        assertEquals(5, pila.prioridadTope());
        assertEquals(1, pila.tamano());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMeteNullLanzaExcepcion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete(null, 5);
    }

    @Test
    public void testMayorPrioridadQuedaEnTope() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("Baja", 1);
        pila.mete("Alta", 10);
        pila.mete("Media", 5);

        assertEquals("Alta", pila.tope());
        assertEquals(10, pila.prioridadTope());
    }

    @Test
    public void testOrdenPorPrioridad() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 2);
        pila.mete("B", 10);
        pila.mete("C", 5);
        pila.mete("D", 8);

        assertEquals("B", pila.obtener(0));
        assertEquals("D", pila.obtener(1));
        assertEquals("C", pila.obtener(2));
        assertEquals("A", pila.obtener(3));
    }

    @Test
    public void testMismaPrioridadMantieneLIFO() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("Primero", 5);
        pila.mete("Segundo", 5);
        pila.mete("Tercero", 5);

        assertEquals("Tercero", pila.saca());
        assertEquals("Segundo", pila.saca());
        assertEquals("Primero", pila.saca());
    }

    @Test(expected = NoSuchElementException.class)
    public void testTopePilaVaciaLanzaExcepcion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.tope();
    }

    @Test(expected = NoSuchElementException.class)
    public void testPrioridadTopePilaVaciaLanzaExcepcion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.prioridadTope();
    }

    @Test
    public void testSacaElemento() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);

        String eliminado = pila.saca();

        assertEquals("A", eliminado);
        assertEquals("B", pila.tope());
        assertEquals(1, pila.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void testSacaPilaVaciaLanzaExcepcion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.saca();
    }

    @Test
    public void testSacarTodosDejaPilaVacia() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);

        pila.saca();
        pila.saca();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testVaciar() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);
        pila.mete("C", 1);

        pila.vaciar();

        assertTrue(pila.esVacio());
        assertEquals(0, pila.tamano());
    }

    @Test
    public void testContiene() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);

        assertTrue(pila.contiene("A"));
        assertTrue(pila.contiene("B"));
        assertFalse(pila.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 5);

        assertFalse(pila.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("Baja", 1);
        pila.mete("Alta", 10);
        pila.mete("Media", 5);

        assertEquals(0, pila.indiceDe("Alta"));
        assertEquals(1, pila.indiceDe("Media"));
        assertEquals(2, pila.indiceDe("Baja"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 1);

        assertEquals(-1, pila.indiceDe("B"));
    }

    @Test
    public void testIndiceDeNullDevuelveMenosUno() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 1);

        assertEquals(-1, pila.indiceDe(null));
    }

    @Test
    public void testObtener() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);
        pila.mete("C", 1);

        assertEquals("A", pila.obtener(0));
        assertEquals("B", pila.obtener(1));
        assertEquals("C", pila.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceFueraDeRango() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 1);

        pila.obtener(1);
    }

    @Test
    public void testBuscar() {
        PilaPrioridad<Integer> pila = new PilaPrioridad<>();

        pila.mete(10, 1);
        pila.mete(30, 3);
        pila.mete(20, 2);

        Integer resultado = pila.buscar(numero -> numero > 15);

        assertEquals(Integer.valueOf(30), resultado);
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        PilaPrioridad<Integer> pila = new PilaPrioridad<>();

        pila.mete(10, 1);
        pila.mete(20, 2);

        assertNull(pila.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarConCriterioNullLanzaExcepcion() {
        PilaPrioridad<Integer> pila = new PilaPrioridad<>();

        pila.buscar(null);
    }

    @Test
    public void testAgregarUsaPrioridadCero() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.agregar("A");
        pila.mete("B", 5);

        assertEquals("B", pila.obtener(0));
        assertEquals("A", pila.obtener(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullLanzaExcepcion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.agregar(null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAgregarPorIndiceNoPermitido() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.agregar(0, "A");
    }

    @Test
    public void testRemoverPorIndice() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);
        pila.mete("C", 1);

        String eliminado = pila.remover(1);

        assertEquals("B", eliminado);
        assertEquals(2, pila.tamano());
        assertEquals("A", pila.obtener(0));
        assertEquals("C", pila.obtener(1));
    }

    @Test
    public void testRemoverIndiceCero() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);

        assertEquals("A", pila.remover(0));
        assertEquals("B", pila.tope());
        assertEquals(1, pila.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativo() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceFueraDeRango() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 1);

        pila.remover(1);
    }

    @Test
    public void testRemoverElemento() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);
        pila.mete("B", 5);
        pila.mete("C", 1);

        boolean eliminado = pila.remover("B");

        assertTrue(eliminado);
        assertFalse(pila.contiene("B"));
        assertEquals(2, pila.tamano());
    }

    @Test
    public void testRemoverElementoInexistente() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);

        assertFalse(pila.remover("B"));
        assertEquals(1, pila.tamano());
    }

    @Test
    public void testRemoverNullDevuelveFalse() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 10);

        assertFalse(pila.remover((String) null));
    }

    @Test
    public void testRemoverElementoDuplicadoEliminaPrimeraAparicion() {
        PilaPrioridad<String> pila = new PilaPrioridad<>();

        pila.mete("A", 5);
        pila.mete("B", 3);
        pila.mete("A", 10);

        assertTrue(pila.remover("A"));

        assertEquals("A", pila.obtener(0));
        assertEquals("B", pila.obtener(1));
        assertEquals(2, pila.tamano());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOrdenarNoPermitido() {
        PilaPrioridad<Integer> pila = new PilaPrioridad<>();

        pila.mete(10, 5);

        pila.ordenar(Comparator.naturalOrder());
    }
}