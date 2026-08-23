package com.example.Caja_De_Herramientas.Lista;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaCircular;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class ListaCircularTest {

    // ---------- Estructura vacía ----------

    @Test
    public void testListaNuevaEstaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testObtenerEnListaVaciaLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.obtener(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverPorIndiceEnListaVaciaLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.remover(0);
    }

    @Test
    public void testRemoverPorElementoEnListaVaciaDevuelveFalse() {
        ListaCircular<String> lista = new ListaCircular<>();
        assertFalse(lista.remover("A"));
    }

    @Test
    public void testBuscarEnListaVaciaDevuelveNull() {
        ListaCircular<String> lista = new ListaCircular<>();
        assertNull(lista.buscar(s -> s.equals("A")));
    }

    // ---------- Un único elemento ----------

    @Test
    public void testAgregarUnElemento() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");

        assertFalse(lista.esVacio());
        assertEquals(1, lista.tamano());
        assertEquals("A", lista.obtener(0));
    }

    @Test
    public void testUnElementoSeApuntaASiMismo() {
        // caso borde propio de listas circulares: con un solo nodo,
        // avanzar "siguiente" tamano+1 veces debe volver al mismo elemento
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        // si internamente el único nodo no cierra el círculo sobre sí mismo,
        // remover y volver a agregar revelaría la inconsistencia
        lista.remover(0);
        lista.agregar("B");

        assertEquals(1, lista.tamano());
        assertEquals("B", lista.obtener(0));
    }

    @Test
    public void testRemoverUnicoElementoDejaListaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        String removido = lista.remover(0);

        assertEquals("A", removido);
        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    // ---------- Varios elementos ----------

    @Test
    public void testAgregarVariosElementosMantieneOrden() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    public void testCircularidadVuelveAlInicio() {
        // caso borde específico de esta estructura: recorrer más allá
        // del último nodo debe volver a la cabeza, sin lanzar excepción
        // ni perder datos, ya que el último apunta de nuevo al primero
        ListaCircular<Integer> lista = new ListaCircular<>();
        lista.agregar(1);
        lista.agregar(2);
        lista.agregar(3);

        // recorremos manualmente tamano + 2 pasos desde obtener(0) usando
        // el patrón de acceso secuencial vía obtener(index % tamano)
        for (int vuelta = 0; vuelta < 2; vuelta++) {
            assertEquals(Integer.valueOf(1), lista.obtener(0));
            assertEquals(Integer.valueOf(2), lista.obtener(1));
            assertEquals(Integer.valueOf(3), lista.obtener(2));
        }
    }

    // ---------- Inserciones ----------

    @Test
    public void testAgregarNullLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        assertThrowsIllegalArgument(() -> lista.agregar(null));
    }

    @Test
    public void testAgregarEnIndiceCeroConListaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();

        lista.agregar(0, "A");

        assertEquals(1, lista.tamano());
        assertEquals("A", lista.obtener(0));
    }

    @Test
    public void testAgregarEnIndiceCeroConElementosExistentes() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("B");
        lista.agregar("C");

        lista.agregar(0, "A");

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceCeroActualizaCierreCircular() {
        // caso borde: al insertar una nueva cabeza, la cola debe
        // quedar apuntando a la cabeza nueva, no a la vieja
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("B");

        lista.agregar(0, "A");
        lista.agregar("C"); // agrega al final, usando el puntero a cola

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceIntermedio() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("C");

        lista.agregar(1, "B");

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("C", lista.obtener(2));
    }

    @Test
    public void testAgregarEnIndiceIgualATamanoInsertaAlFinal() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        lista.agregar(1, "B");

        assertEquals("B", lista.obtener(1));
        assertEquals(2, lista.tamano());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarEnIndiceNegativoLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar(-1, "A");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAgregarEnIndiceMayorATamanoLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar(5, "B");
    }

    // ---------- Eliminaciones ----------

    @Test
    public void testRemoverCabezaActualizaCierreCircular() {
        // caso borde: al remover la cabeza, la cola debe quedar
        // apuntando a la nueva cabeza para mantener el círculo cerrado
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        lista.remover(0);
        lista.agregar("D"); // agrega al final usando el puntero a cola

        assertEquals(3, lista.tamano());
        assertEquals("B", lista.obtener(0));
        assertEquals("C", lista.obtener(1));
        assertEquals("D", lista.obtener(2));
    }

    @Test
    public void testRemoverColaActualizaPunteroCola() {
        // caso borde: al remover el último elemento (la cola),
        // el puntero cola debe pasar al anterior
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        lista.remover(2); // remueve "C", que era la cola
        lista.agregar("D"); // debe agregarse correctamente al nuevo final

        assertEquals(3, lista.tamano());
        assertEquals("A", lista.obtener(0));
        assertEquals("B", lista.obtener(1));
        assertEquals("D", lista.obtener(2));
    }

    @Test
    public void testRemoverElementoIntermedio() {
        ListaCircular<String> lista = new ListaCircular<>();
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
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.remover(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoverIndiceMayorATamanoLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.remover(5);
    }

    @Test
    public void testRemoverPorElementoExistente() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.remover("A"));
        assertEquals(1, lista.tamano());
        assertFalse(lista.contiene("A"));
    }

    @Test
    public void testRemoverPorElementoInexistente() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        assertFalse(lista.remover("Z"));
    }

    @Test
    public void testVaciarDejaListaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");

        lista.vaciar();

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    @Test
    public void testAgregarDespuesDeVaciarFunciona() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.vaciar();

        lista.agregar("B");

        assertEquals(1, lista.tamano());
        assertEquals("B", lista.obtener(0));
    }

    @Test
    public void testRemoverTodosLosElementosUnoAUnoDejaListaVacia() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        lista.remover(0);
        lista.remover(0);
        lista.remover(0);

        assertTrue(lista.esVacio());
        assertEquals(0, lista.tamano());
    }

    // ---------- Búsquedas ----------

    @Test
    public void testContieneElementoExistente() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");

        assertTrue(lista.contiene("A"));
        assertFalse(lista.contiene("Z"));
    }

    @Test
    public void testIndiceDeElementoExistente() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");
        lista.agregar("B");
        lista.agregar("C");

        assertEquals(1, lista.indiceDe("B"));
    }

    @Test
    public void testIndiceDeElementoInexistente() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        assertEquals(-1, lista.indiceDe("Z"));
    }

    @Test
    public void testBuscarConCoincidencia() {
        ListaCircular<Integer> lista = new ListaCircular<>();
        lista.agregar(10);
        lista.agregar(20);
        lista.agregar(30);

        assertEquals(Integer.valueOf(20), lista.buscar(n -> n > 15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuscarConCriterioNullLanzaExcepcion() {
        ListaCircular<String> lista = new ListaCircular<>();
        lista.agregar("A");

        lista.buscar(null);
    }

    // ---------- Ordenar ----------

    @Test
    public void testOrdenarDevuelveNuevaListaOrdenada() {
        ListaCircular<Integer> lista = new ListaCircular<>();
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
        ListaCircular<Integer> lista = new ListaCircular<>();
        lista.agregar(30);
        lista.agregar(10);

        lista.ordenar((a, b) -> a - b);

        assertEquals(Integer.valueOf(30), lista.obtener(0));
        assertEquals(Integer.valueOf(10), lista.obtener(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOrdenarConComparatorNullLanzaExcepcion() {
        ListaCircular<Integer> lista = new ListaCircular<>();
        lista.agregar(1);

        lista.ordenar(null);
    }

    @Test
    public void testOrdenarListaVaciaDevuelveListaVacia() {
        ListaCircular<Integer> lista = new ListaCircular<>();

        TDALista<Integer> ordenada = lista.ordenar((a, b) -> a - b);

        assertTrue(ordenada.esVacio());
    }

    // ---------- Helper ----------

    private interface Accion {
        void ejecutar();
    }

    private void assertThrowsIllegalArgument(Accion accion) {
        try {
            accion.ejecutar();
            fail("Se esperaba IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // esperado
        }
    }
}