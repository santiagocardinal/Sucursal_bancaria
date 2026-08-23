package com.example.Caja_De_Herramientas.Lista;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaArrayTest {

    // ---------- Estructura vacía ----------

    @Test
    public void testListaNuevaEstaVacia() {
        ListaArray<String> lista = new ListaArray<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerEnListaVaciaLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.obtener(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverPorIndiceEnListaVaciaLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.remover(0);
    }

    @Test
    public void testRemoverPorElementoEnListaVaciaDevuelveFalse() {
        ListaArray<String> lista = new ListaArray<>();
        assertFalse(lista.remover("A"));
    }

    @Test
    public void testContieneEnListaVaciaDevuelveFalse() {
        ListaArray<String> lista = new ListaArray<>();
        assertFalse(lista.contiene("A"));
    }

    @Test
    public void testBuscarEnListaVaciaDevuelveNull() {
        ListaArray<String> lista = new ListaArray<>();
        assertNull(lista.buscar(s -> s.equals("A")));
    }

    // ---------- Un único elemento ----------

    @Test
    public void testAgregarUnElemento() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals("A", lista.obtener(0));
    }

    @Test
    public void testRemoverUnicoElementoDejaListaVacia() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        String removido = lista.remover(0);

        assertEquals("A", removido);
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    // ---------- Varios elementos ----------

    @Test
    public void testAgregarVariosElementosMantieneOrden() {
        ListaArray<String> lista = new ListaArray<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    // ---------- Inserciones ----------

    @Test
    public void testAgregarNullLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        assertThrows_IllegalArgumentException(() -> lista.agregar(null));
    }

    @Test
    public void testAgregarEnIndiceInsertaCorrectamente() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("C");

        lista.agregar(1, "B");

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceCero() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("B");

        lista.agregar(0, "A");

        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
    }

    @Test
    public void testAgregarEnIndiceIgualATamano() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        lista.agregar(1, "B"); // insertar al final por índice

        assertEquals("B", lista.obtener(1));
        assertEquals(2, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarEnIndiceNegativoLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar(-1, "A");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarEnIndiceMayorATamanoLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar(5, "B");
    }

    @Test
    public void testAgregarNullEnIndiceLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        assertThrows_IllegalArgumentException(() -> lista.agregar(0, null));
    }

    @Test
    public void testRedimensionamientoAlSuperarCapacidadInicial() {
        ListaArray<Integer> lista = new ListaArray<>();

        // la capacidad inicial es 10; agregamos 15 para forzar el redimensionamiento
        for (int i = 0; i < 15; i++) {
            lista.agregar(i);
        }

        assertEquals(15, lista.tamano());
        for (int i = 0; i < 15; i++) {
            assertEquals(Integer.valueOf(i), lista.obtener(i));
        }
    }

    // ---------- Eliminaciones ----------

    @Test
    public void testRemoverPorIndiceDesplazaElementos() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        String removido = lista.remover(1);

        assertEquals("B", removido);
        assertEquals(2, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("C", lista.obtener(1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceNegativoLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceMayorATamanoLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.remover(5);
    }

    @Test
    public void testRemoverPorElementoExistente() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        boolean removido = lista.remover("B");

        assertTrue(removido);
        assertEquals(2, lista.tamano());
        assertFalse(lista.contiene("B"));
    }

    @Test
    public void testRemoverPorElementoInexistente() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        assertFalse(lista.remover("Z"));
        assertEquals(1, lista.tamano());
    }

    @Test
    public void testRemoverPorElementoNullDevuelveFalse() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        assertFalse(lista.remover((String) null));
    }

    @Test
    public void testVaciarDejaListaVacia() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarDespuesDeVaciarFunciona() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.vaciar();

        lista.agregar("B");

        assertEquals(1, lista.tamano());
        assertEquals("B", lista.obtener(0));
    }

    // ---------- Búsquedas ----------

    @Test
    public void testContieneElementoExistente() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertTrue(lista.contiene("B"));
        assertFalse(lista.contiene("C"));
    }

    @Test
    public void testIndiceDeElementoExistente() {
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

        assertEquals(-1, lista.indiceDe("Z"));
    }

    @Test
    public void testIndiceDeNullDevuelveMenosUno() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        assertEquals(-1, lista.indiceDe(null));
    }

    @Test
    public void testBuscarConCoincidencia() {
        ListaArray<Integer> lista = new ListaArray<>();
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        Integer resultado = lista.buscar(numero -> numero > 15);

        assertEquals(Integer.valueOf(20), resultado);
    }

    @Test
    public void testBuscarSinCoincidenciaDevuelveNull() {
        ListaArray<Integer> lista = new ListaArray<>();
        lista.agregar(10);

        assertNull(lista.buscar(numero -> numero > 100));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarConCriterioNullLanzaExcepcion() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");

        lista.buscar(null);
    }

    // ---------- Ordenar ----------

    @Test
    public void testOrdenarDevuelveNuevaListaOrdenada() {
        ListaArray<Integer> lista = new ListaArray<>();
        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        TDALista<Integer> ordenada = lista.ordenar((a, b) -> a - b);

        assertEquals(Integer.valueOf(10), ordenada.obtener(0));
        assertEquals(Integer.valueOf(20), ordenada.obtener(1));
        assertEquals(Integer.valueOf(30), ordenada.obtener(2));
    }

    @Test
    public void testOrdenarNoModificaLaListaOriginal() {
        ListaArray<Integer> lista = new ListaArray<>();
        lista.agregar(30);
        lista.agregar(10);
        lista.agregar(20);

        lista.ordenar((a, b) -> a - b);

        // la lista original debe mantener su orden de inserción
        assertEquals(Integer.valueOf(30), lista.obtener(0));
        assertEquals(Integer.valueOf(10), lista.obtener(1));
        assertEquals(Integer.valueOf(20), lista.obtener(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarConComparatorNullLanzaExcepcion() {
        ListaArray<Integer> lista = new ListaArray<>();
        lista.agregar(1);

        lista.ordenar(null);
    }

    @Test
    public void testOrdenarListaVaciaDevuelveListaVacia() {
        ListaArray<Integer> lista = new ListaArray<>();

        TDALista<Integer> ordenada = lista.ordenar((a, b) -> a - b);

        assertTrue(ordenada.esVacio());
    }

    // ---------- Casos borde ----------

    @Test
    public void testAgregarYRemoverRepetidamenteMantieneConsistencia() {
        ListaArray<Integer> lista = new ListaArray<>();

        lista.agregar(1);
        lista.agregar(2);
        lista.remover(0);
        lista.agregar(3);

        assertEquals(2, lista.tamano());
        assertEquals(Integer.valueOf(2), lista.obtener(0));
        assertEquals(Integer.valueOf(3), lista.obtener(1));
    }

    @Test
    public void testElementosDuplicadosIndiceDeDevuelvePrimeraOcurrencia() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("A");

        assertEquals(0, lista.indiceDe("A"));
    }

    @Test
    public void testRemoverPorElementoConDuplicadosRemuevePrimeraOcurrencia() {
        ListaArray<String> lista = new ListaArray<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("A");

        lista.remover("A");

        assertEquals(2, lista.tamano());
        assertEquals("B", lista.obtener(0));
        assertEquals("A", lista.obtener(1));
    }

    // ---------- Helper ----------

    private interface Accion {
        void ejecutar();
    }

    private void assertThrows_IllegalArgumentException(Accion accion) {
        try {
            accion.ejecutar();
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}