package com.example.Caja_de_Herramientas.Arboles;

import com.Entidades.Cliente;

public class NodoOperador implements NodoExpresion {
    private final char operador;
    private final NodoExpresion izq;
    private final NodoExpresion der;

    public NodoOperador(char operador, NodoExpresion izq, NodoExpresion der) {
        this.operador = operador;
        this.izq = izq;
        this.der = der;
    }

    @Override
    public double evaluar(Cliente cliente) {
        double valIzq = izq.evaluar(cliente);
        double valDer = der.evaluar(cliente);

        switch (operador) {
            case '+': return valIzq + valDer;
            case '-': return valIzq - valDer;
            case '*': return valIzq * valDer;
            case '/': 
                if (valDer == 0) throw new ArithmeticException("División por cero en la fórmula");
                return valIzq / valDer;
            default: throw new UnsupportedOperationException("Operador no soportado: " + operador);
        }
    }

    @Override
    public int getPrecedencia() {
        if (operador == '+' || operador == '-') return 1;
        if (operador == '*' || operador == '/') return 2;
        return 0;
    }

    @Override
    public String aTextoSinParentesisRedundantes(int precedenciaPadre) {
        int miPrecedencia = getPrecedencia();
        String representacion = izq.aTextoSinParentesisRedundantes(miPrecedencia) 
                                + " " + operador + " " 
                                + der.aTextoSinParentesisRedundantes(miPrecedencia + (operador == '-' || operador == '/' ? 1 : 0));

        if (precedenciaPadre > miPrecedencia) {
            return "(" + representacion + ")";
        }
        return representacion;
    }
}