package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.EstadoProducto;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class ClienteTest {

    @Test
    public void testConstructorGuardaCi() {
        Cliente cliente = new Cliente("12345678");

        assertEquals("12345678", cliente.getCi());
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorCiNullLanzaExcepcion() {
        new Cliente(null);
    }

    @Test
    public void testNumeroTurnoInicialEsCero() {
        Cliente cliente = new Cliente("12345678");

        assertEquals(0, cliente.getNumeroTurno());
    }

    @Test
    public void testSetNumeroTurno() {
        Cliente cliente = new Cliente("12345678");

        cliente.setNumeroTurno(10);

        assertEquals(10, cliente.getNumeroTurno());
    }

    @Test
    public void testSolicitudActualInicialmenteEsNull() {
        Cliente cliente = new Cliente("12345678");

        assertNull(cliente.getSolicitudActual());
    }

    @Test
    public void testSetSolicitudActual() {
        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.CONSULTA, "T1", 0, new ListaEnlazada<>());
        cliente.setSolicitudActual(solicitud);

        assertSame(solicitud, cliente.getSolicitudActual());
    }

    @Test
    public void testProductosInicialmenteVacios() {
        Cliente cliente = new Cliente("12345678");
        ListaEnlazada<IProducto> productos = cliente.obtenerProductos();

        assertNotNull(productos);
        assertTrue(productos.esVacio());
        assertEquals(0, productos.tamano());
    }

    @Test
    public void testAgregarProducto() {
        Cliente cliente = new Cliente("12345678");
        IProducto producto = new TarjetaDeCredito("T1");

        cliente.agregarProducto(producto);

        assertEquals(1, cliente.obtenerProductos().tamano());
        assertSame(producto, cliente.obtenerProducto("T1"));
    }

    @Test
    public void testObtenerProductoEntreVarios() {
        Cliente cliente = new Cliente("12345678");
        IProducto producto1 = new TarjetaDeCredito("T1");
        IProducto producto2 = new TarjetaDeCredito("T2");
        IProducto producto3 = new TarjetaDeCredito("T3");

        cliente.agregarProducto(producto1);
        cliente.agregarProducto(producto2);
        cliente.agregarProducto(producto3);

        assertSame(producto2, cliente.obtenerProducto("T2"));
    }

    @Test
    public void testObtenerProductoInexistenteDevuelveNull() {
        Cliente cliente = new Cliente("12345678");
        cliente.agregarProducto(new TarjetaDeCredito("T1"));

        assertNull(cliente.obtenerProducto("T99"));
    }

    @Test
    public void testQuitarProducto() {
        Cliente cliente = new Cliente("12345678");
        IProducto producto = new TarjetaDeCredito("T1");

        cliente.agregarProducto(producto);

        boolean eliminado = cliente.quitarProducto("T1");

        assertTrue(eliminado);
        assertNull(cliente.obtenerProducto("T1"));
        assertEquals(0, cliente.obtenerProductos().tamano());
    }

    @Test
    public void testQuitarProductoMantieneLosDemas() {
        Cliente cliente = new Cliente("12345678");
        IProducto producto1 = new TarjetaDeCredito("T1");
        IProducto producto2 = new TarjetaDeCredito("T2");
        IProducto producto3 = new TarjetaDeCredito("T3");

        cliente.agregarProducto(producto1);
        cliente.agregarProducto(producto2);
        cliente.agregarProducto(producto3);

        boolean eliminado = cliente.quitarProducto("T2");

        assertTrue(eliminado);
        assertSame(producto1, cliente.obtenerProducto("T1"));
        assertNull(cliente.obtenerProducto("T2"));
        assertSame(producto3, cliente.obtenerProducto("T3"));
        assertEquals(2, cliente.obtenerProductos().tamano());
    }

    @Test
    public void testQuitarProductoInexistenteDevuelveFalse() {
        Cliente cliente = new Cliente("12345678");
        cliente.agregarProducto(new TarjetaDeCredito("T1"));

        assertFalse(cliente.quitarProducto("T99"));
        assertEquals(1, cliente.obtenerProductos().tamano());
    }

    @Test
    public void testQuitarTodosLosProductos() {
        Cliente cliente = new Cliente("12345678");
        cliente.agregarProducto(new TarjetaDeCredito("T1"));
        cliente.agregarProducto(new TarjetaDeCredito("T2"));
        cliente.agregarProducto(new TarjetaDeCredito("T3"));

        cliente.quitarProducto("T1");
        cliente.quitarProducto("T2");
        cliente.quitarProducto("T3");

        assertTrue(cliente.obtenerProductos().esVacio());
        assertEquals(0, cliente.obtenerProductos().tamano());
    }

    @Test
    public void testTarjetaDeCreditoSePuedeCambiarDeEstado() {
        TarjetaDeCredito tarjeta = new TarjetaDeCredito("T1");

        tarjeta.modificarEstado(EstadoProducto.VENCIDO);

        assertEquals(EstadoProducto.VENCIDO, tarjeta.getEstado());
        assertTrue(tarjeta.estaVencido());
    }
}
