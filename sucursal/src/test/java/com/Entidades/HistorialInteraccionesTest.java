package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoInteraccion;

public class HistorialInteraccionesTest {

    @Test
    public void testRegistrarInteraccionCorrectamente() {
        HistorialInteracciones historial = new HistorialInteracciones();
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1");

        historial.registrarInteraccion(interaccion);

        Pila<Interaccion> resultado = historial.obtenerPorCliente("C1");

        assertEquals(1, resultado.tamano());
        assertEquals(interaccion, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarInteraccionNulaLanzaExcepcion() {
        HistorialInteracciones historial = new HistorialInteracciones();

        historial.registrarInteraccion(null);
    }

    @Test
    public void testObtenerPorTipoDevuelveSoloDelTipo() {
        HistorialInteracciones historial = new HistorialInteracciones();
        Interaccion i1 = new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1");
        Interaccion i2 = new Interaccion(TipoInteraccion.PAGO, "C2", "M1");
        Interaccion i3 = new Interaccion(TipoInteraccion.CONSULTA, "C3", "M2");

        historial.registrarInteraccion(i1);
        historial.registrarInteraccion(i2);
        historial.registrarInteraccion(i3);

        Pila<Interaccion> resultado = historial.obtenerPorTipo(TipoInteraccion.CONSULTA);

        assertEquals(2, resultado.tamano());

        while (!resultado.esVacio()) {
            Interaccion actual = resultado.saca();
            assertEquals(TipoInteraccion.CONSULTA, actual.getTipo());
        }
    }

    @Test
    public void testObtenerPorTipoSinCoincidenciasDevuelvePilaVacia() {
        HistorialInteracciones historial = new HistorialInteracciones();
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1"));

        Pila<Interaccion> resultado = historial.obtenerPorTipo(TipoInteraccion.PAGO);

        assertTrue(resultado.esVacio());
    }

    @Test
    public void testObtenerPorClienteDevuelveSoloDelCliente() {
        HistorialInteracciones historial = new HistorialInteracciones();
        Interaccion i1 = new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1");
        Interaccion i2 = new Interaccion(TipoInteraccion.PAGO, "C2", "M1");
        Interaccion i3 = new Interaccion(TipoInteraccion.OTROS, "C1", "M2");

        historial.registrarInteraccion(i1);
        historial.registrarInteraccion(i2);
        historial.registrarInteraccion(i3);

        Pila<Interaccion> resultado = historial.obtenerPorCliente("C1");

        assertEquals(2, resultado.tamano());

        while (!resultado.esVacio()) {
            Interaccion actual = resultado.saca();
            assertEquals("C1", actual.getClienteId());
        }
    }

    @Test
    public void testObtenerPorClienteSinCoincidenciasDevuelvePilaVacia() {
        HistorialInteracciones historial = new HistorialInteracciones();
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1"));

        Pila<Interaccion> resultado = historial.obtenerPorCliente("C2");

        assertTrue(resultado.esVacio());
    }

    @Test
    public void testFiltrarNoEliminaInteraccionesOriginales() {
        HistorialInteracciones historial = new HistorialInteracciones();
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1"));
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.PAGO, "C1", "M2"));

        historial.obtenerPorTipo(TipoInteraccion.CONSULTA);

        Pila<Interaccion> resultado = historial.obtenerPorCliente("C1");

        assertEquals(2, resultado.tamano());
    }

    @Test
    public void testContarPorTipoCuentaCorrectamente() {
        HistorialInteracciones historial = new HistorialInteracciones();
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1"));
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C2", "M1"));
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.PAGO, "C1", "M2"));

        ListaEnlazada<ConteoInteraccion> conteos = historial.contarPorTipo();

        assertEquals(2, conteos.tamano());

        ConteoInteraccion consultaConteo = conteos.buscar(c -> c.getTipo() == TipoInteraccion.CONSULTA);
        ConteoInteraccion pagoConteo = conteos.buscar(c -> c.getTipo() == TipoInteraccion.PAGO);

        assertNotNull(consultaConteo);
        assertEquals(2, consultaConteo.getCantidad());
        assertNotNull(pagoConteo);
        assertEquals(1, pagoConteo.getCantidad());
    }

    @Test
    public void testContarPorTipoConHistorialVacioDevuelveListaVacia() {
        HistorialInteracciones historial = new HistorialInteracciones();

        ListaEnlazada<ConteoInteraccion> conteos = historial.contarPorTipo();

        assertTrue(conteos.esVacio());
        assertEquals(0, conteos.tamano());
    }

    @Test
    public void testContarPorTipoNoEliminaInteraccionesOriginales() {
        HistorialInteracciones historial = new HistorialInteracciones();
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.CONSULTA, "C1", "M1"));
        historial.registrarInteraccion(new Interaccion(TipoInteraccion.PAGO, "C2", "M1"));

        historial.contarPorTipo();

        Pila<Interaccion> resultado = historial.obtenerPorCliente("C1");

        assertEquals(1, resultado.tamano());
    }
}