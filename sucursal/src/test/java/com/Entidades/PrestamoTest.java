package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Enums.EstadoProducto;

public class PrestamoTest {

    @Test
    public void crearPrestamoCorrectamente() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        assertEquals("PRE001", prestamo.getId());
        assertEquals(100000, prestamo.getMontoOriginal(), 0.001);
        assertEquals(0.10, prestamo.getInteres(), 0.001);
        assertEquals(10, prestamo.getCuotasTotales());
        assertEquals(0, prestamo.getCuotasActual());
        assertEquals(EstadoProducto.ACTIVO, prestamo.getEstado());
    }

    @Test
    public void calcularProximaCuotaCorrectamente() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        double cuota = prestamo.proximaCuota();

        assertEquals(11000, cuota, 0.001);
    }

    @Test
    public void pagarCuotaAumentaCuotasActuales() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        double cuota = prestamo.proximaCuota();

        prestamo.pagarCuota(cuota);

        assertEquals(1, prestamo.getCuotasActual());
    }

    @Test
    public void pagarVariasCuotasCorrectamente() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        double cuota = prestamo.proximaCuota();

        prestamo.pagarCuota(cuota);
        prestamo.pagarCuota(cuota);
        prestamo.pagarCuota(cuota);

        assertEquals(3, prestamo.getCuotasActual());
    }

    @Test
    public void pagarTodasLasCuotasCancelaPrestamo() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            3
        );

        double cuota = prestamo.proximaCuota();

        prestamo.pagarCuota(cuota);
        prestamo.pagarCuota(cuota);
        prestamo.pagarCuota(cuota);

        assertEquals(3, prestamo.getCuotasActual());
        assertEquals(
            EstadoProducto.CANCELADO,
            prestamo.getEstado()
        );
    }

    @Test
    public void proximaCuotaEsCeroSiPrestamoEstaPago() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            2
        );

        double cuota = prestamo.proximaCuota();

        prestamo.pagarCuota(cuota);
        prestamo.pagarCuota(cuota);

        assertEquals(0, prestamo.proximaCuota(), 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void noPermitePagarMasCuotasDeLasTotales() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            1
        );

        prestamo.pagarCuota(prestamo.proximaCuota());

        prestamo.pagarCuota(0);
    }

    @Test
    public void modificarEstadoCorrectamente() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        prestamo.modificarEstado(
            EstadoProducto.VENCIDO
        );

        assertEquals(
            EstadoProducto.VENCIDO,
            prestamo.getEstado()
        );

        assertTrue(prestamo.estaVencido());
    }

    @Test
    public void prestamoActivoNoEstaVencido() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        assertFalse(prestamo.estaVencido());
    }

    @Test(expected = NullPointerException.class)
    public void noPermiteIdNulo() {

        new Prestamo(
            null,
            100000,
            0.10,
            10
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteMontoCero() {

        new Prestamo(
            "PRE001",
            0,
            0.10,
            10
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteMontoNegativo() {

        new Prestamo(
            "PRE001",
            -100000,
            0.10,
            10
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteInteresNegativo() {

        new Prestamo(
            "PRE001",
            100000,
            -0.10,
            10
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteCuotasTotalesCero() {

        new Prestamo(
            "PRE001",
            100000,
            0.10,
            0
        );
    }

    @Test(expected = NullPointerException.class)
    public void noPermiteEstadoNulo() {

        Prestamo prestamo = new Prestamo(
            "PRE001",
            100000,
            0.10,
            10
        );

        prestamo.modificarEstado(null);
    }
}