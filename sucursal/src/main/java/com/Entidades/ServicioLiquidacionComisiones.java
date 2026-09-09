package com.Entidades;

import com.example.Caja_de_Herramientas.Arboles.AVLImpl;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.TipoInteraccion;

public class ServicioLiquidacionComisiones {

    // Clase interna e inmutable (todos los campos son final) que representael resultado de liquidar la comisión de UN cliente puntual
    public static class ResultadoLiquidacion 
    {
        private final String ciCliente;
        private final double montoComision;

        public ResultadoLiquidacion(String ciCliente, double montoComision) {
            this.ciCliente = ciCliente;
            this.montoComision = montoComision;
        }

        public String getCiCliente(){ 
            return ciCliente; 
        }
        
        public double getMontoComision(){ 
            return montoComision; 
        }
    }

    // calcula la comisión de cada cliente del AVL usando  la fórmula recibida, y devuelve la lista completa de resultados.
    // Si se le pasa un historial real, además audita cada liquidación.
    public ListaEnlazada<ResultadoLiquidacion> liquidar(AVLImpl<Cliente> indiceClientes,FormulaComision formula,HistorialInteracciones historial) {

        // Sin fórmula configurada no hay nada que calcular se corta acá
        if (formula == null) throw new IllegalStateException("No hay fórmula configurada para liquidar");

        // Acá se van juntando los resultados de cada cliente
        ListaEnlazada<ResultadoLiquidacion> resultados = new ListaEnlazada<>();

        // inOrder recorre el AVL completo en orden (por ci), visitando a todos los clientes exactamente una vez.
        indiceClientes.inOrder(cliente -> {

            // Se le pide a la fórmula que calcule la comisión para ese cliente  
            double comision = formula.calcularParaCliente(cliente);

            // Se guarda el resultado de este cliente en la lista de salida
            resultados.agregar(new ResultadoLiquidacion(cliente.getCi(), comision));

            // Si se pasó un historial real, se deja registro de
            // auditoría de que a este cliente se le liquidó/pagó una
            // comisión, se identifica usando el ennum SISTEMA_LIQUIDACION
            if (historial != null) {
                // Registramos la interacción utilizando pagp, el ID del cliente y un identificador del proceso automático
                historial.registrarInteraccion(new Interaccion(TipoInteraccion.PAGO,cliente.getCi(),"SISTEMA_LIQUIDACION" ));
            }
        });
        // Se devuelven los resultados de todos los clientes ya liquidados
        return resultados;
    }

    // simular hace exactamente el mismo cálculo que liquidar(),
    // pero pasando null como historial. Gracias a ese chequeo de arriba
    // (if (historial != null)), simular una fórmula nueva calcula los
    // montos sin dejar ningún rastro de auditoría, es decir, "probar" una
    // fórmula sin que quede registrado como si realmente se hubiera pagado.
    public ListaEnlazada<ResultadoLiquidacion> simular(AVLImpl<Cliente> indiceClientes,FormulaComision formulaSimulada) {

        if (formulaSimulada == null) throw new IllegalArgumentException("La fórmula simulada no puede ser nula");

        // Reutiliza toda la lógica de liquidar(), sin duplicar código
        return liquidar(indiceClientes, formulaSimulada, null);
    }
}