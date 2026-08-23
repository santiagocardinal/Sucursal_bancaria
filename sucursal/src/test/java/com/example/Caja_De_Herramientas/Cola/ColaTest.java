package com.example.Caja_de_Herramientas.Cola;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Cola.Cola;

public class ColaTest {

    // ---------- Estructura vacía ----------

    @Test
    public void testColaNuevaEstaVacia() {
        Cola<String> cola = new Cola<>();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void testFrenteColaVaciaLanzaExcepcion() {
        Cola<String> cola = new Cola<>();
        cola.frente();
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitaDeColaVaciaLanzaExcepcion() {
        Cola<String> cola = new Cola<>();
        cola.quitaDeCola();
    }

    // ---------- Un único elemento ----------

    @Test
    public void testPoneEnColaUnElemento() {
        Cola<String> cola = new Cola<>();

        boolean resultado = cola.poneEnCola("A");

        assertTrue(resultado);
        assertFalse(cola.esVacio());
        assertEquals(1, cola.tamano());
        assertEquals("A", cola.frente());
    }

    @Test
    public void testQuitaDeColaConUnElementoLaDejaVacia() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");

        String eliminado = cola.quitaDeCola();

        assertEquals("A", eliminado);
        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    // ---------- Varios elementos: orden FIFO ----------

    @Test
    public void testOrdenFIFO() {
        Cola<String> cola = new Cola<>();

        cola.poneEnCola("Primero");
        cola.poneEnCola("Segundo");
        cola.poneEnCola("Tercero");

        assertEquals("Primero", cola.frente());
        assertEquals("Primero", cola.quitaDeCola());
        assertEquals("Segundo", cola.quitaDeCola());
        assertEquals("Tercero", cola.quitaDeCola());
    }

    @Test
    public void testFrenteNoModificaLaCola() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.poneEnCola("B");

        cola.frente();
        cola.frente();

        assertEquals(2, cola.tamano());
        assertEquals("A", cola.frente());
    }

    @Test
    public void testTamanoCreceYDecreceCorrectamente() {
        Cola<String> cola = new Cola<>();

        assertEquals(0, cola.tamano());

        cola.poneEnCola("A");
        assertEquals(1, cola.tamano());

        cola.poneEnCola("B");
        assertEquals(2, cola.tamano());

        cola.quitaDeCola();
        assertEquals(1, cola.tamano());

        cola.quitaDeCola();
        assertEquals(0, cola.tamano());
    }

    // ---------- Inserciones ----------

    @Test
    public void testAgregarEsEquivalenteAPoneEnCola() {
        Cola<String> cola = new Cola<>();

        cola.agregar("A"); // heredado de ListaEnlazada
        cola.poneEnCola("B");

        assertEquals(2, cola.tamano());
        assertEquals("A", cola.quitaDeCola());
        assertEquals("B", cola.quitaDeCola());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPoneEnColaNullLanzaExcepcion() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola(null);
    }

    // ---------- Eliminaciones ----------

    @Test
    public void testQuitarTodosLosElementosDejaColaVacia() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.poneEnCola("B");
        cola.poneEnCola("C");

        cola.quitaDeCola();
        cola.quitaDeCola();
        cola.quitaDeCola();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testAnulaVaciaLaCola() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.poneEnCola("B");

        cola.anula();

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testVaciarEsEquivalenteAAnula() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");

        cola.vaciar(); // heredado de ListaEnlazada

        assertTrue(cola.esVacio());
        assertEquals(0, cola.tamano());
    }

    @Test
    public void testPoneEnColaDespuesDeVaciarFunciona() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.anula();

        cola.poneEnCola("B");

        assertEquals(1, cola.tamano());
        assertEquals("B", cola.frente());
    }

    // ---------- Búsquedas (heredadas de ListaEnlazada) ----------

    @Test
    public void testContiene() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.poneEnCola("B");

        assertTrue(cola.contiene("A"));
        assertTrue(cola.contiene("B"));
        assertFalse(cola.contiene("C"));
    }

    @Test
    public void testIndiceDe() {
        Cola<String> cola = new Cola<>();
        cola.poneEnCola("A");
        cola.poneEnCola("B");
        cola.poneEnCola("C");

        assertEquals(0, cola.indiceDe("A"));
        assertEquals(1, cola.indiceDe("B"));
        assertEquals(2, cola.indiceDe("C"));
        assertEquals(-1, cola.indiceDe("Z"));
    }

    @Test
    public void testBuscar() {
        Cola<Integer> cola = new Cola<>();
        cola.poneEnCola(10);
        cola.poneEnCola(20);
        cola.poneEnCola(30);

        Integer resultado = cola.buscar(numero -> numero > 15);

        assertEquals(Integer.valueOf(20), resultado);
    }

    @Test
    public void testBuscarSinResultadoDevuelveNull() {
        Cola<Integer> cola = new Cola<>();
        cola.poneEnCola(10);

        assertNull(cola.buscar(numero -> numero > 100));
    }

    // ---------- Casos borde ----------

    @Test
    public void testEncolarYDesencolarRepetidamenteMantieneOrden() {
        Cola<Integer> cola = new Cola<>();

        cola.poneEnCola(1);
        cola.poneEnCola(2);
        assertEquals(Integer.valueOf(1), cola.quitaDeCola());

        cola.poneEnCola(3);
        assertEquals(Integer.valueOf(2), cola.quitaDeCola());
        assertEquals(Integer.valueOf(3), cola.quitaDeCola());

        assertTrue(cola.esVacio());
    }

    @Test(expected = NoSuchElementException.class)
    public void testQuitarDespuesDeVaciarLanzaExcepcion() {
    Cola<String> cola = new Cola<>();
    cola.poneEnCola("A");
    cola.quitaDeCola();

    cola.quitaDeCola(); 
    }
}