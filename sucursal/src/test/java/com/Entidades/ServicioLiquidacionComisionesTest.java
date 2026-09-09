package com.Entidades;

import org.junit.Test;
import static org.junit.Assert.*;
import com.example.Caja_de_Herramientas.Arboles.AVLImpl;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.Moneda;
import com.example.Enums.TipoInteraccion;

public class ServicioLiquidacionComisionesTest {
    @Test
    public void testLiquidarComisionesCalculaYRegistraEnHistorial() {
        ServicioLiquidacionComisiones servicio = new ServicioLiquidacionComisiones();
        AVLImpl<Cliente> indiceClientes = new AVLImpl<>();
        HistorialInteracciones historial = new HistorialInteracciones();

        Cliente c1 = new Cliente("31922160");
        c1.agregarProducto(new Cuenta("CTA1", 1000.0, Moneda.PESO_URUGUAYO));
        indiceClientes.insertar(c1);

        FormulaComision formula = new FormulaComision("FORM1", "sal * 0.10");
        ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> resultados = servicio.liquidar(indiceClientes,
                formula, historial);

        assertEquals(1, resultados.tamano());
        assertEquals("31922160", resultados.obtener(0).getCiCliente());
        assertEquals(100.0, resultados.obtener(0).getMontoComision(), 0.001);
        assertEquals(1, historial.obtenerPorTipo(TipoInteraccion.PAGO).tamano());

    }

    @Test
    public void testSimularCalculoSinModificarHistorial() {
        ServicioLiquidacionComisiones servicio = new ServicioLiquidacionComisiones();
        AVLImpl<Cliente> indiceClientes = new AVLImpl<>();

        Cliente c1 = new Cliente("23456789");
        c1.agregarProducto(new Cuenta("CTA1", 2000.0, Moneda.PESO_URUGUAYO));
        indiceClientes.insertar(c1);

        FormulaComision formula = new FormulaComision("FORM1", "sal * 0.05");

        ListaEnlazada<ServicioLiquidacionComisiones.ResultadoLiquidacion> simulacion = servicio.simular(indiceClientes,
                formula);

        assertEquals(1, simulacion.tamano());
        assertEquals("23456789", simulacion.obtener(0).getCiCliente());
        assertEquals(50.0, simulacion.obtener(0).getMontoComision(), 0.001);
    }

    @Test(expected = IllegalStateException.class)
    public void testLiquidarConFormulaNulaLanzaExcepcion() {
        ServicioLiquidacionComisiones servicio = new ServicioLiquidacionComisiones();
        AVLImpl<Cliente> indiceClientes = new AVLImpl<>();

        servicio.liquidar(indiceClientes, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSimularConFormulaNulaLanzaExcepcion() {
        ServicioLiquidacionComisiones servicio = new ServicioLiquidacionComisiones();
        AVLImpl<Cliente> indiceClientes = new AVLImpl<>();

        servicio.simular(indiceClientes, null);
    }

    //che ahí ves algo?

}
