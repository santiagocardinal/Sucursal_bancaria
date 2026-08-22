package com.example.EstrategiasDeAtencion;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.example.Enums.TipoInteraccion;

public class SolicitudAtencionTest {

    @Test
    public void testConstructorGuardaTipoIdYMontoParaPago() {
        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.PAGO, "C123", 1500.75);

        assertEquals(TipoInteraccion.PAGO, solicitud.getTipoInteraccion());
        assertEquals("C123", solicitud.getIdProducto());
        assertEquals(1500.75, solicitud.getMonto(), 0.0);
    }

    @Test
    public void testConstructorGuardaMontoParaAltaProducto() {
        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.ALTA_PRODUCTO, "TC123", 2500.50);

        assertEquals(2500.50, solicitud.getMonto(), 0.0);
    }

    @Test
    public void testConstructorPoneMontoEnCeroParaBajaProducto() {
        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.BAJA_PRODUCTO, "TC123", 2500.50);

        assertEquals(0.0, solicitud.getMonto(), 0.0);
    }

    @Test
    public void testConstructorPoneMontoEnCeroParaModificacion() {
        SolicitudAtencion solicitud = new SolicitudAtencion(TipoInteraccion.MODIFICACION, "TC123", 2500.50);

        assertEquals(0.0, solicitud.getMonto(), 0.0);
    }

    @Test
    public void testConstructorPoneMontoEnCeroParaConsultaYOtros() {
        SolicitudAtencion consulta = new SolicitudAtencion(TipoInteraccion.CONSULTA, "TC123", 2500.50);
        SolicitudAtencion otros = new SolicitudAtencion(TipoInteraccion.OTROS, "TC123", 2500.50);

        assertEquals(0.0, consulta.getMonto(), 0.0);
        assertEquals(0.0, otros.getMonto(), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetTipoInteraccionLanzaExcepcionCuandoEsNull() {
        SolicitudAtencion solicitud = new SolicitudAtencion(null, "TC123", 2500.50);

        solicitud.getTipoInteraccion();
    }
}
