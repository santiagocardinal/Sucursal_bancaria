package com.Entidades;

import com.example.Caja_de_Herramientas.Arboles.AVLImpl;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.TipoInteraccion;

public class ServicioLiquidacionComisiones {

    public static class ResultadoLiquidacion {
        private final String ciCliente;
        private final double montoComision;

        public ResultadoLiquidacion(String ciCliente, double montoComision) {
            this.ciCliente = ciCliente;
            this.montoComision = montoComision;
        }

        public String getCiCliente() { return ciCliente; }
        public double getMontoComision() { return montoComision; }
    }

    public ListaEnlazada<ResultadoLiquidacion> liquidar(
            AVLImpl<Cliente> indiceClientes, 
            FormulaComision formula, 
            HistorialInteracciones historial) {

        if (formula == null) throw new IllegalStateException("No hay fórmula configurada para liquidar");
        
        ListaEnlazada<ResultadoLiquidacion> resultados = new ListaEnlazada<>();

        indiceClientes.inOrder(cliente -> {
            double comision = formula.calcularParaCliente(cliente);
            resultados.agregar(new ResultadoLiquidacion(cliente.getCi(), comision));

            if (historial != null) {
                // Registramos la interacción utilizando PAGO, el ID del cliente y un identificador del proceso automático
                historial.registrarInteraccion(new Interaccion(
                    TipoInteraccion.PAGO,
                    cliente.getCi(),
                    "SISTEMA_LIQUIDACION"
                ));
            }
        });

        return resultados;
    }

    public ListaEnlazada<ResultadoLiquidacion> simular(
            AVLImpl<Cliente> indiceClientes, 
            FormulaComision formulaSimulada) {

        if (formulaSimulada == null) throw new IllegalArgumentException("La fórmula simulada no puede ser nula");

        return liquidar(indiceClientes, formulaSimulada, null);
    }
}