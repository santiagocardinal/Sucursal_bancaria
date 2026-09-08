package com.example.Caja_de_Herramientas.Arboles;

import com.Entidades.Cliente;

// Representa un nodo en el árbol binario de expresión
public abstract class NodoExpresion {
    // Evaluación del subárbol devolviendo el resultado numérico
    public abstract double evaluar(Cliente cliente);

    // Impresión recursiva controlando precedencia de operadores
    public abstract String aTexto(int precedenciaPadre);
}