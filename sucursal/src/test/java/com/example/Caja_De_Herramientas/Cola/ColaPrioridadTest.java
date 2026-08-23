package com.example.Caja_de_Herramientas.Cola;

import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.NoSuchElementException;

import org.junit.Test;

import com.example.Enums.NivelPrioridad;

public class ColaPrioridadTest {

    // ---------- Estructura vacía ----------

    @Test
    public void testColaNuevaEstaVacia() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertTrue(cola.esVacio());
        assertTrue(cola.vacia());
        assertEquals(0, cola.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteColaVaciaLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.frente();
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.quitaDeCola();
    }

    // ---------- Un único elemento ----------

    @Test
    public void testPoneEnColaUnElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        boolean resultado = cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertTrue(resultado);
        assertFalse(cola.esVacio());
        assertEquals(1, cola.tamano());
        assertEquals("A", cola.frente());
    }

    @Test
    public void testPoneEnColaNullDevuelveFalse() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertFalse(cola.poneEnCola(null, NivelPrioridad.NORMAL));
        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testPoneEnColaSinPrioridadUsaNormalPorDefecto() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Normal");
        cola.poneEnCola("Urgente", NivelPrioridad.URGENTE);

        // URGENTE debe quedar primero, ya que NORMAL es la prioridad por defecto (la menos urgente)
        assertEquals("Urgente", cola.obtener(0));
        assertEquals("Normal", cola.obtener(1));
    }

    // ---------- Varios elementos: orden por prioridad ----------

    @Test
    public void testMayorPrioridadQuedaPrimero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Baja", NivelPrioridad.NORMAL);
        cola.poneEnCola("Alta", NivelPrioridad.URGENTE);
        cola.poneEnCola("Media", NivelPrioridad.TURNO_PREVIO);

        assertEquals("Alta", cola.frente());
        assertEquals("Alta", cola.obtener(0));
        assertEquals("Media", cola.obtener(1));
        assertEquals("Baja", cola.obtener(2));
    }

    @Test
    public void testMismaPrioridadMantieneOrdenDeLlegadaFIFO() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Primero", NivelPrioridad.NORMAL);
        cola.poneEnCola("Segundo", NivelPrioridad.NORMAL);
        cola.poneEnCola("Tercero", NivelPrioridad.NORMAL);

        assertEquals("Primero", cola.quitaDeCola());
        assertEquals("Segundo", cola.quitaDeCola());
        assertEquals("Tercero", cola.quitaDeCola());
    }

    @Test
    public void testUrgenteInsertadoAlFinalIgualQuedaPrimero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Normal1", NivelPrioridad.NORMAL);
        cola.poneEnCola("Normal2", NivelPrioridad.NORMAL);
        cola.poneEnCola("Urgente", NivelPrioridad.URGENTE);

        assertEquals("Urgente", cola.frente());
    }

    // ---------- Inserciones (agregar con prioridad NORMAL por defecto) ----------

    @Test
    public void testAgregarUsaPrioridadNormal() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.agregar("A");
        cola.poneEnCola("B", NivelPrioridad.URGENTE);

        assertEquals("B", cola.obtener(0));
        assertEquals("A", cola.obtener(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarNullLanzaExcepcion() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.agregar(null);
    }

    // ---------- Eliminaciones ----------

    @Test
    public void testQuitaDeCola() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        String eliminado = cola.quitaDeCola();

        assertEquals("A", eliminado);
        assertEquals("B", cola.frente());
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testQuitarTodosLosElementosDejaColaVacia() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        cola.quitaDeCola();
        cola.quitaDeCola();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testQuitarElementoPorValor() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.TURNO_PREVIO);
        cola.poneEnCola("C", NivelPrioridad.NORMAL);

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
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertNull(cola.quitar("B"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testEliminarElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        assertTrue(cola.eliminar("B"));
        assertFalse(cola.contiene("B"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testEliminarElementoInexistente() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.URGENTE);

        assertFalse(cola.eliminar("B"));
    }

    @Test
    public void testQuitarPorIndice() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.TURNO_PREVIO);
        cola.poneEnCola("C", NivelPrioridad.NORMAL);

        String eliminado = cola.quitar(1);

        assertEquals("B", eliminado);
        assertEquals(2, cola.tamano());
        assertEquals("A", cola.obtener(0));
        assertEquals("C", cola.obtener(1));
    }

    @Test
    public void testQuitarIndiceCero() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        assertEquals("A", cola.quitar(0));
        assertEquals("B", cola.frente());
        assertEquals(1, cola.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testQuitarIndiceInvalido() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        cola.quitar(5);
    }

    @Test
    public void testRemoverPorIndice() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        assertEquals("B", cola.remover(1));
        assertEquals(1, cola.tamano());
        assertFalse(cola.contiene("B"));
    }

    @Test
    public void testRemoverPorElemento() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        assertTrue(cola.remover("A"));
        assertFalse(cola.contiene("A"));
        assertEquals(1, cola.tamano());
    }

    @Test
    public void testAnula() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        cola.anula();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testVaciar() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        cola.vaciar();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    // ---------- Búsquedas ----------

    @Test
    public void testContiene() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.NORMAL);

        assertTrue(cola.contiene("A"));
        assertTrue(cola.contiene("B"));
        assertFalse(cola.contiene("C"));
    }

    @Test
    public void testContieneNullDevuelveFalse() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertFalse(cola.contiene(null));
    }

    @Test
    public void testIndiceDe() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("Baja", NivelPrioridad.NORMAL);
        cola.poneEnCola("Alta", NivelPrioridad.URGENTE);
        cola.poneEnCola("Media", NivelPrioridad.TURNO_PREVIO);

        assertEquals(0, cola.indiceDe("Alta"));
        assertEquals(1, cola.indiceDe("Media"));
        assertEquals(2, cola.indiceDe("Baja"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertEquals(-1, cola.indiceDe("B"));
    }

    @Test
    public void testObtener() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.TURNO_PREVIO);
        cola.poneEnCola("C", NivelPrioridad.NORMAL);

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
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        cola.obtener(1);
    }

    @Test
    public void testBuscar() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, NivelPrioridad.URGENTE);
        cola.poneEnCola(30, NivelPrioridad.TURNO_PREVIO);
        cola.poneEnCola(50, NivelPrioridad.NORMAL);

        Integer resultado = cola.buscar(numero -> numero > 20);

        // buscar recorre desde el frente; el primero que matchea es el de mayor prioridad que cumpla
        assertNotNull(resultado);
        assertTrue(resultado > 20);
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();

        cola.poneEnCola(10, NivelPrioridad.URGENTE);
        cola.poneEnCola(20, NivelPrioridad.NORMAL);

        assertNull(cola.buscar(numero -> numero > 100));
    }

    @Test
    public void testBuscarConCriterioNullDevuelveNull() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();
        cola.poneEnCola(10, NivelPrioridad.NORMAL);

        assertNull(cola.buscar(null));
    }

    // ---------- Tamaño ----------

    @Test
    public void testTamano() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertEquals(0, cola.tamano());

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        assertEquals(1, cola.tamano());

        cola.poneEnCola("B", NivelPrioridad.NORMAL);
        assertEquals(2, cola.tamano());

        cola.quitaDeCola();
        assertEquals(1, cola.tamano());
    }

    // ---------- Casos borde: operaciones no soportadas ----------

    @Test(expected = UnsupportedOperationException.class)
    public void testAgregarPorIndiceNoPermitido() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.agregar(0, "A");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testOrdenarNoPermitido() {
        ColaPrioridad<Integer> cola = new ColaPrioridad<>();
        cola.poneEnCola(10, NivelPrioridad.NORMAL);

        cola.ordenar(Comparator.naturalOrder());
    }

    // ---------- posicionDe ----------

    @Test
    public void testPosicionDe() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        cola.poneEnCola("A", NivelPrioridad.URGENTE);
        cola.poneEnCola("B", NivelPrioridad.TURNO_PREVIO);
        cola.poneEnCola("C", NivelPrioridad.NORMAL);

        assertEquals(0, cola.posicionDe("A"));
        assertEquals(1, cola.posicionDe("B"));
        assertEquals(2, cola.posicionDe("C"));
    }

    @Test
    public void testPosicionDeElementoInexistente() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertEquals(-1, cola.posicionDe("Z"));
    }

    @Test
    public void testPosicionDeColaVacia() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();

        assertEquals(-1, cola.posicionDe("A"));
    }

    @Test
    public void testPosicionDeNullDevuelveMenosUno() {
        ColaPrioridad<String> cola = new ColaPrioridad<>();
        cola.poneEnCola("A", NivelPrioridad.NORMAL);

        assertEquals(-1, cola.posicionDe(null));
    }
}