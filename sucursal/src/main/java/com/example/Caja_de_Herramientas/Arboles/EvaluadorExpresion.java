package com.example.Caja_de_Herramientas.Arboles;

import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Caja_de_Herramientas.Pila.TDAPila;

public class EvaluadorExpresion {

    public static NodoExpresion construirArbol(String expr) {
        if (expr == null || expr.trim().isEmpty()) return null;

        String limpia = expr.replaceAll("\\s+", "");
        TDAPila<NodoExpresion> nodos = new Pila<>();
        TDAPila<Character> operadores = new Pila<>();

        int i = 0;
        while (i < limpia.length()) {
            char c = limpia.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (i < limpia.length() && (Character.isLetterOrDigit(limpia.charAt(i)) || limpia.charAt(i) == '.')) {
                    sb.append(limpia.charAt(i));
                    i++;
                }
                i--;
                nodos.mete(new NodoOperando(sb.toString()));
            } else if (c == '(') {
                operadores.mete(c);
            } else if (c == ')') {
                while (!operadores.esVacio() && operadores.tope() != '(') {
                    desapilarYCrearSubarbol(nodos, operadores);
                }
                if (!operadores.esVacio()) operadores.saca(); // Quitar '('
            } else if (esOperador(c)) {
                while (!operadores.esVacio() && precedencia(operadores.tope()) >= precedencia(c)) {
                    desapilarYCrearSubarbol(nodos, operadores);
                }
                operadores.mete(c);
            }
            i++;
        }

        while (!operadores.esVacio()) {
            desapilarYCrearSubarbol(nodos, operadores);
        }

        return nodos.esVacio() ? null : nodos.saca();
    }

    private static void desapilarYCrearSubarbol(TDAPila<NodoExpresion> nodos, TDAPila<Character> operadores) {
        if (operadores.esVacio() || nodos.esVacio()) return;
        char op = operadores.saca();
        NodoExpresion der = nodos.saca();
        NodoExpresion izq = nodos.saca();
        nodos.mete(new NodoOperador(op, izq, der));
    }

    private static boolean esOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static int precedencia(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        return 0;
    }
}