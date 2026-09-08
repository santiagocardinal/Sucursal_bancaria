package com.example.Caja_de_Herramientas.Arboles;

import com.Entidades.Cliente;

public class NodoOperando implements NodoExpresion {
    private final String simbolo;
    private final boolean esVariable;

    public NodoOperando(String simbolo) {
        this.simbolo = simbolo.trim();
        this.esVariable = simbolo.matches("[a-zA-Z]+");
    }

    @Override
    public double evaluar(Cliente cliente) {
        if (!esVariable) {
            return Double.parseDouble(simbolo);
        }

        if (cliente == null) return 0.0;

        // Mapeo directo de variables con los datos del Cliente
        switch (simbolo.toLowerCase()) {
            case "sal":
                return cliente.obtenerProductos().tamano() * 1000.0; // Ajustar según método de saldo
            case "mov":
                return 5.0; // Movimientos / Interacciones
            case "pro":
                return (double) cliente.obtenerProductos().tamano();
            default:
                throw new IllegalArgumentException("Variable no soportada en la fórmula: " + simbolo);
        }
    }

    @Override
    public String aTextoSinParentesisRedundantes(int precedenciaPadre) {
        return simbolo;
    }

    @Override
    public int getPrecedencia() {
        return Integer.MAX_VALUE;
    }
}