package com.example.Caja_De_Herramientas.Cola;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Cola.ColaPrioridad;

public class ColaPrioridadTest {

    @Test
    public void testColaNuevaEstaVacia() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertTrue(cola.esVacio());
        assertTrue(cola.vacia());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testPoneEnColaUnElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        boolean resultado = cola.poneEnCola("A", 5);

        assertTrue(resultado);
        assertFalse(cola.esVacio());
        assertEquals(1, cola.tamano());
        assertEquals("A", cola.frente());
    }

    @Test
    public void testPoneEnColaNullDevuelveFalse() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertFalse(cola.poneEnCola(null, 5));
        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testMayorPrioridadQuedaPrimero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Baja", 1);
        cola.poneEnCola("Alta", 10);
        cola.poneEnCola("Media", 5);

        assertEquals("Alta", cola.frente());

        assertEquals("Alta", cola.obtener(0));
        assertEquals("Media", cola.obtener(1));
        assertEquals("Baja", cola.obtener(2));
    }

    @Test
    public void testOrdenCompletoPorPrioridad() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 2);
        cola.poneEnCola("B", 10);
        cola.poneEnCola("C", 5);
        cola.poneEnCola("D", 8);

        assertEquals("B", cola.obtener(0));
        assertEquals("D", cola.obtener(1));
        assertEquals("C", cola.obtener(2));
        assertEquals("A", cola.obtener(3));
    }

    @Test
    public void testMismaPrioridadMantieneFIFO() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Primero", 5);
        cola.poneEnCola("Segundo", 5);
        cola.poneEnCola("Tercero", 5);

        assertEquals("Primero", cola.quitaDeCola());
        assertEquals("Segundo", cola.quitaDeCola());
        assertEquals("Tercero", cola.quitaDeCola());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteColaVaciaLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.frente();
    }

    @Test
    public void testQuitaDeCola() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        String eliminado = cola.quitaDeCola();

        assertEquals("A", eliminado);
        assertEquals("B", cola.frente());
        assertEquals(1, cola.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.quitaDeCola();
    }

    @Test
    public void testQuitarTodosLosElementosDejaColaVacia() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        cola.quitaDeCola();
        cola.quitaDeCola();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testPoneEnColaSinPrioridadUsaPrioridadCero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Normal");
        cola.poneEnCola("Urgente", 10);

        assertEquals("Urgente", cola.obtener(0));
        assertEquals("Normal", cola.obtener(1));
    }

    @Test
    public void testAgregarUsaPrioridadCero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.agregar("A");
        cola.poneEnCola("B", 5);

        assertEquals("B", cola.obtener(0));
        assertEquals("A", cola.obtener(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.agregar(null);
    }

    @Test
    public void testTamano() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertEquals(0, cola.tamano());

        cola.poneEnCola("A", 1);
        assertEquals(1, cola.tamano());

        cola.poneEnCola("B", 2);
        assertEquals(2, cola.tamano());

        cola.quitaDeCola();
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testContiene() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        assertTrue(cola.contiene("A"));
        assertTrue(cola.contiene("B"));
        assertFalse(cola.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 1);

        assertFalse(cola.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Baja", 1);
        cola.poneEnCola("Alta", 10);
        cola.poneEnCola("Media", 5);

        assertEquals(0, cola.indiceDe("Alta"));
        assertEquals(1, cola.indiceDe("Media"));
        assertEquals(2, cola.indiceDe("Baja"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 1);

        assertEquals(-1, cola.indiceDe("B"));
    }

    @Test
    public void testObtener() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);
        cola.poneEnCola("C", 1);

        assertEquals("A", cola.obtener(0));
        assertEquals("B", cola.obtener(1));
        assertEquals("C", cola.obtener(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceNegativo() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.obtener(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerIndiceMayorAlTamano() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 1);

        cola.obtener(1);
    }

    @Test
    public void testBuscar() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, 1);
        cola.poneEnCola(30, 2);
        cola.poneEnCola(50, 3);

        Integer resultado = cola.buscar(numero -> numero > 20);

        assertEquals(Integer.valueOf(50), resultado);
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, 1);
        cola.poneEnCola(20, 2);

        assertNull(cola.buscar(numero -> numero > 100));
    }

    @Test
    public void testBuscarConCriterioNullDevuelveNull() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, 1);

        assertNull(cola.buscar(null));
    }

    @Test
    public void testQuitarElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);
        cola.poneEnCola("C", 1);

        String eliminado = cola.quitar("B");

        assertEquals("B", eliminado);
        assertFalse(cola.contiene("B"));
        assertEquals(2, cola.tamano());

        assertEquals("A", cola.obtener(0));
        assertEquals("C", cola.obtener(1));
    }

    @Test
    public void testQuitarElementoInexistenteDevuelveNull() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 1);

        assertNull(cola.quitar("B"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testEliminarElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        assertTrue(cola.eliminar("B"));

        assertFalse(cola.contiene("B"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testEliminarElementoInexistente() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);

        assertFalse(cola.eliminar("B"));
    }

    @Test
    public void testQuitarPorIndice() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);
        cola.poneEnCola("C", 1);

        String eliminado = cola.quitar(1);

        assertEquals("B", eliminado);
        assertEquals(2, cola.tamano());

        assertEquals("A", cola.obtener(0));
        assertEquals("C", cola.obtener(1));
    }

    @Test
    public void testQuitarIndiceCero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        assertEquals("A", cola.quitar(0));

        assertEquals("B", cola.frente());
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testQuitarPorIndiceConElementosDuplicados() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 8);
        cola.poneEnCola("A", 5);

        String eliminado = cola.quitar(2);

        assertEquals("A", eliminado);

        // El primer A debe seguir existiendo
        assertEquals("A", cola.obtener(0));
        assertEquals("B", cola.obtener(1));
        assertEquals(2, cola.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuitarIndiceInvalido() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 1);

        cola.quitar(5);
    }

    @Test
    public void testRemoverPorIndice() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        assertEquals("B", cola.remover(1));

        assertEquals(1, cola.tamano());
        assertFalse(cola.contiene("B"));
    }

    @Test
    public void testRemoverPorElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        assertTrue(cola.remover("A"));

        assertFalse(cola.contiene("A"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testAnula() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        cola.anula();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testVaciar() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", 10);
        cola.poneEnCola("B", 5);

        cola.vaciar();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAgregarPorIndiceNoPermitido() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.agregar(0, "A");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOrdenarNoPermitido() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, 5);

        cola.ordenar(Comparator.naturalOrder());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testInvertirNoPermitido() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.invertir();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOrdenarTotalNoPermitido() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.ordenarTotal(Comparator.naturalOrder());
    }
}