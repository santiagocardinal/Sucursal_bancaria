package com.Entidades;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.Moneda;
import com.example.Enums.NivelPrioridad;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class SucursalTest {

    private SolicitudAtencion crearSolicitud() {
        return new SolicitudAtencion(TipoInteraccion.CONSULTA, "T1", 0, new ListaEnlazada<>());
    }

    @Test
    public void testConstructorGuardaId() {
        Sucursal sucursal = new Sucursal("SUC1");

        assertNotNull(sucursal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorIdNuloLanzaExcepcion() {
        new Sucursal(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAgregarSectorNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.agregarSector(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarInteraccionNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.registrarInteraccion(null);
    }

    @Test
    public void testRegistrarInteraccionCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), "M1");
        sucursal.registrarInteraccion(interaccion);

        Pila<Interaccion> resultado = sucursal.obtenerHistorialCliente(cliente.getCi());

        assertEquals(1, resultado.tamano());
        assertEquals(interaccion, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarDocumentoNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.registrarDocumento(null);
    }

    @Test
    public void testRegistrarDocumentoCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

        Documento documento = new Documento("DOC001", TipoDocumento.CONTRATO, cliente, LocalDate.now(), null);
        sucursal.registrarDocumento(documento);

        Pila<Documento> resultado = sucursal.obtenerDocumentosCliente(cliente.getCi());

        assertEquals(1, resultado.tamano());
        assertEquals(documento, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObtenerHistorialClienteInexistenteLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.obtenerHistorialCliente("99999999");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testObtenerDocumentosClienteInexistenteLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");

        sucursal.obtenerDocumentosCliente("99999999");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorClienteNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);

        sucursal.registrarClienteEnSector(null, sector, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSectorNuloLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, null, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorPrioridadNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, null, crearSolicitud());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSolicitudNulaLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRegistrarClienteEnSectorSectorNoRegistradoLanzaExcepcion() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        Cliente cliente = new Cliente("12345678");

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());
    }

    @Test
    public void testRegistrarClienteEnSectorCorrectamente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);
        Cliente cliente = new Cliente("12345678");
        SolicitudAtencion solicitud = crearSolicitud();

        sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, solicitud);

        assertEquals(0, sector.estimarPosicionEnCola(cliente));
        assertSame(solicitud, cliente.getSolicitudActual());
    }

    @Test
    public void obtenerClientesEnRangoDeDocumento() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);

        Cliente c1 = new Cliente("29999999");
        Cliente c2 = new Cliente("30000000");
        Cliente c3 = new Cliente("41234567");
        Cliente c4 = new Cliente("50000000");
        Cliente c5 = new Cliente("50000001");

        SolicitudAtencion sol = new SolicitudAtencion(TipoInteraccion.CONSULTA, null, 0, new ListaEnlazada<>());

        sucursal.registrarClienteEnSector(c1, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c2, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c3, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c4, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c5, sector, NivelPrioridad.NORMAL, sol);

        TDALista<Cliente> rango = sucursal.obtenerClientesEnRangoDeDocumento("30000000", "50000000");

        assertEquals(3, rango.tamano());
        assertEquals("30000000", rango.obtener(0).getCi());
        assertEquals("41234567", rango.obtener(1).getCi());
        assertEquals("50000000", rango.obtener(2).getCi());
    }

    @Test
    public void obtenerClienteConDocumentoAnteriorYSiguiente() {
        Sucursal sucursal = new Sucursal("SUC1");
        Sector sector = new Sector(sucursal);
        sucursal.agregarSector(sector);

        Cliente c1 = new Cliente("31204561");
        Cliente c2 = new Cliente("43501238");
        Cliente c3 = new Cliente("51123409");

        SolicitudAtencion sol = new SolicitudAtencion(TipoInteraccion.CONSULTA, null, 0, new ListaEnlazada<>());

        sucursal.registrarClienteEnSector(c1, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c2, sector, NivelPrioridad.NORMAL, sol);
        sucursal.registrarClienteEnSector(c3, sector, NivelPrioridad.NORMAL, sol);

        Cliente anterior = sucursal.obtenerClienteConDocumentoAnterior("43501238");
        assertNotNull(anterior);
        assertEquals("31204561", anterior.getCi());

        Cliente siguiente = sucursal.obtenerClienteConDocumentoSiguiente("43501238");
        assertNotNull(siguiente);
        assertEquals("51123409", siguiente.getCi());

        assertNull(sucursal.obtenerClienteConDocumentoAnterior("31204561"));
        assertNull(sucursal.obtenerClienteConDocumentoSiguiente("51123409"));
    }

    @Test
public void testBuscarClientePorCiExistente() {

    Sucursal sucursal = new Sucursal("SUC1");

    Sector sector = new Sector(sucursal);

    sucursal.agregarSector(sector);

    Cliente cliente = new Cliente("12345678");

    sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud() );

    Cliente encontrado = sucursal.buscarClientePorCi("12345678");

    assertNotNull(encontrado);

    assertEquals("12345678", encontrado.getCi());
    }

    @Test
public void testBuscarClientePorCiInexistente() {

    Sucursal sucursal = new Sucursal("SUC1");

    Cliente encontrado = sucursal.buscarClientePorCi("99999999");

    assertNull(encontrado);
    }

    @Test
public void testBuscarProductoPorCuentaDirecto() {

    Sucursal sucursal = new Sucursal("SUC1");
    Sector sector = new Sector(sucursal);
    sucursal.agregarSector(sector);

    Cliente cliente = new Cliente("12345678");
    sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

    Cuenta cuenta = new Cuenta("CTA1", 1000.0, Moneda.PESO_URUGUAYO);
    cliente.agregarProducto(cuenta);

    IProducto encontrado = sucursal.buscarProductoPorCuenta("12345678", "CTA1");

    assertNotNull(encontrado);
    assertEquals("CTA1", encontrado.getId());
    }

    @Test
public void testBuscarProductoPorCuentaDentroDePaquete() {

    Sucursal sucursal = new Sucursal("SUC1");
    Sector sector = new Sector(sucursal);
    sucursal.agregarSector(sector);

    Cliente cliente = new Cliente("12345678");
    sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

    PaqueteProducto paquete = new PaqueteProducto("PAQ1", Moneda.PESO_URUGUAYO);
    Cuenta cuenta = new Cuenta("CTA2", 2000.0, Moneda.PESO_URUGUAYO);

    paquete.agregarComponente(cuenta);
    cliente.agregarProducto(paquete);

    IProducto encontrado = sucursal.buscarProductoPorCuenta("12345678", "CTA2");

    assertNotNull(encontrado);
    assertEquals("CTA2", encontrado.getId());
    }

    @Test
public void testBuscarProductoPorCuentaEnPaqueteAnidado() {

    Sucursal sucursal = new Sucursal("SUC1");
    Sector sector = new Sector(sucursal);
    sucursal.agregarSector(sector);

    Cliente cliente = new Cliente("12345678");
    sucursal.registrarClienteEnSector(cliente, sector, NivelPrioridad.NORMAL, crearSolicitud());

    PaqueteProducto paquete1 = new PaqueteProducto("PAQ1", Moneda.PESO_URUGUAYO);
    PaqueteProducto paquete2 = new PaqueteProducto("PAQ2", Moneda.PESO_URUGUAYO);
    Cuenta cuenta = new Cuenta("CTA3", 3000.0, Moneda.PESO_URUGUAYO);

    paquete2.agregarComponente(cuenta);
    paquete1.agregarComponente(paquete2);
    cliente.agregarProducto(paquete1);

    IProducto encontrado = sucursal.buscarProductoPorCuenta("12345678", "CTA3");

    assertNotNull(encontrado);
    assertEquals("CTA3", encontrado.getId());
    }

    @Test
public void testListarCarteraOrdenadaPorCi() {

    Sucursal sucursal = new Sucursal("SUC1");
    Sector sector = new Sector(sucursal);
    sucursal.agregarSector(sector);

    Cliente cliente1 = new Cliente("50000000");
    Cliente cliente2 = new Cliente("30000000");
    Cliente cliente3 = new Cliente("40000000");

    sucursal.registrarClienteEnSector(cliente1, sector, NivelPrioridad.NORMAL, crearSolicitud());
    sucursal.registrarClienteEnSector(cliente2, sector, NivelPrioridad.NORMAL, crearSolicitud());
    sucursal.registrarClienteEnSector(cliente3, sector, NivelPrioridad.NORMAL, crearSolicitud());

    ListaEnlazada<Cliente> cartera = sucursal.listarCarteraOrdenadaPorCi();

    assertEquals(3, cartera.tamano());
    assertEquals("30000000", cartera.obtener(0).getCi());
    assertEquals("40000000", cartera.obtener(1).getCi());
    assertEquals("50000000", cartera.obtener(2).getCi());
    }

    
}