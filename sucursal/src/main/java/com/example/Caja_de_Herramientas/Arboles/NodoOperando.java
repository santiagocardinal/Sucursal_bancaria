package com.example.Caja_de_Herramientas.Arboles;

public class NodoOperando extends NodoExpresion {
    private final String valor; // Puede ser un número "100" o variable "saldoPromedio"

    public NodoOperando(String valor) {
        this.valor = valor;
    }

    @Override
    public double evaluar(Cliente cliente) {
        // Si es un número constante
        try {
            return Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            // Si es una variable, se extrae del cliente
            return obtenerValorVariable(cliente, valor);
        }
    }

    private double obtenerValorVariable(Cliente cliente, String variable) {
        if (cliente == null) return 0.0;
        
        switch (variable) {
            case "cantidadProductos":
                return cliente.obtenerProductos().tamano();
            case "numeroTurno":
                return cliente.getNumeroTurno();
            // Acá se pueden agregar más variables definidas por el grupo
            default:
                throw new IllegalArgumentException("Variable no reconocida: " + variable);
        }
    }

    @Override
    public String aTexto(int precedenciaPadre) {
        return valor; // Un operando nunca lleva paréntesis por sí solo
    }
}
    

