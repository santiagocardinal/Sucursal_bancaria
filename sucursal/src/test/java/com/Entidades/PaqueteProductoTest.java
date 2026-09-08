package com.Entidades;

import static org.junit.Assert.*;
import org.junit.Test;
import com.example.Enums.Moneda;

public class PaqueteProductoTest {
    @Test
    public void testObtenerComponenteDeMayorSaldoEnComponenteAnidado() {
        PaqueteProducto paquetePrincipal = new PaqueteProducto("PAQ1", Moneda.PESO_URUGUAYO);
        Cuenta cuentaChica = new Cuenta("CTA-CHICA", 1500.0, Moneda.PESO_URUGUAYO);
        PaqueteProducto subPaquete = new PaqueteProducto("PAQ-SUB", Moneda.PESO_URUGUAYO);
        Cuenta cuentaMediana = new Cuenta("CTA-MED", 3500.0, Moneda.PESO_URUGUAYO);
        Cuenta cuentaGrande = new Cuenta("CTA-GRND", 12000.0, Moneda.PESO_URUGUAYO);

        subPaquete.agregarComponente(cuentaGrande);
        subPaquete.agregarComponente(cuentaMediana);

        paquetePrincipal.agregarComponente(cuentaChica);
        paquetePrincipal.agregarComponente(subPaquete);

        Cuenta mejorCuenta = paquetePrincipal.obtenerComponenteDeMayorSaldo();
        assertNotNull(mejorCuenta);
        assertEquals("CTA-GRND", mejorCuenta.getId());
        assertEquals(12000.0, mejorCuenta.getSaldo(), 0.0001);
    }

    @Test
    public void testObtenerComponenteDeMayorSaldoEnPaqueteVacioRetornaNull() {
        PaqueteProducto paqueteVacio = new PaqueteProducto("PAQ-VACIO", Moneda.PESO_URUGUAYO);

        Cuenta mejorCuenta = paqueteVacio.obtenerComponenteDeMayorSaldo();
        assertNull(mejorCuenta);
    }
}
