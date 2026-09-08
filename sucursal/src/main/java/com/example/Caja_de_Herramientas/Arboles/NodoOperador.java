package com.example.Caja_de_Herramientas.Arboles;

public class NodoOperador extends NodoExpresion {
    private final char operador;
    private final NodoExpresion izquierdo;
    private final NodoExpresion derecho;

    public NodoOperador(char operador, NodoExpresion izquierdo, NodoExpresion derecho) {
        this.operador = operador;
        this.izquierdo = izquierdo;
        this.derecho = derecho;
    }

    @Override
    public double evaluar(Cliente cliente) {
        double valIzq = izquierdo.evaluar(cliente);
        double valDer = derecho.evaluar(cliente);

        switch (operador) {
            case '+': return valIzq + valDer;
            case '-': return valIzq - valDer;
            case '*': return valIzq * valDer;
            case '/': 
                if (valDer == 0) throw new ArithmeticException("División por cero en fórmula");
                return valIzq / valDer;
            default:
                throw new UnsupportedOperationException("Operador no soportado: " + operador);
        }
    }

    @Override
    public String aTexto(int precedenciaPadre) {
        int miPrecedencia = obtenerPrecedencia(this.operador);
        
        // Recorrido Inorden de los subárboles
        String textoIzq = izquierdo.aTexto(miPrecedencia);
        String textoDer = derecho.aTexto(miPrecedencia);
        String resultado = textoIzq + " " + operador + " " + textoDer;

        // Si la precedencia de este nodo es menor que la de su padre,
        // se requiere envolver en paréntesis para mantener la prioridad matemática.
        if (miPrecedencia < precedenciaPadre) {
            return "(" + resultado + ")";
        }
        return resultado;
    }

    public static int obtenerPrecedencia(char op) {
        switch (op) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            default: return 0;
        }
    }
}