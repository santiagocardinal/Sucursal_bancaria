package com.Entidades;

import static org.junit.Assert.*;
import org.junit.Test;
import com.example.Enums.Moneda;

public class FormulaComisionTest {
    @Test
    public void testCrearFormulaGuardaId() {
        FormulaComision formula = new FormulaComision("FORM1", "sal * 0.05");
        assertEquals("FORM1", formula.getId());
    }

    @Test
    public void testCalcularParaClienteConClienteNuloRetornaCero() {
        FormulaComision formula = new FormulaComision("FORM1", "sal * 0.05");
        double resultado = formula.calcularParaCliente(null);

        assertEquals(0.0, resultado, 0.001);
    }

    @Test
    public void testCalcularParaClienteValidoFormulaBasica() {
        FormulaComision formula = new FormulaComision("FORM1", "sal * 0.10");

        Cliente cliente = new Cliente("12345678");
        Cuenta cuenta = new Cuenta("CTA-01", 1000, Moneda.PESO_URUGUAYO);
        cliente.agregarProducto(cuenta);

        double comision = formula.calcularParaCliente(cliente);

        assertEquals(100.0, comision, 0.001);
    }

    @Test
    public void testObtenerFormulaLimpiaSinParentesisQueSobran() {
        FormulaComision formula = new FormulaComision("FORM1", "sal + (pro * 2)");
        String limpia = formula.obtenerFormulaLimpia();

        assertNotNull(limpia);
        assertFalse(limpia.isEmpty());
        assertFalse(limpia.contains("("));
        assertFalse(limpia.contains(")"));
    }
}
