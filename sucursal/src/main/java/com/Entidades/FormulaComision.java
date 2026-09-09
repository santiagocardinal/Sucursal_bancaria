package com.Entidades;

import com.example.Caja_de_Herramientas.Arboles.EvaluadorExpresion;

import com.example.Caja_de_Herramientas.Arboles.NodoExpresion;

public class FormulaComision {

    // Identificador de la fórmula (para poder buscarla oreferenciarla)
    private String id;

    // Guardamos el texto tal cual lo escribió el usuariooadministrador
    private String expresionOriginal;

    private NodoExpresion arbolSintactico;

    public FormulaComision(String id, String expresionOriginal) {

        this.id = id;
        this.expresionOriginal = expresionOriginal;
        this.arbolSintactico = EvaluadorExpresion.construirArbol(expresionOriginal);
    }

    // Método que efectivamente CALCULA la comisión para un cliente puntual, usando la formula que se subio al arbol fórmula
    public double calcularParaCliente(Cliente cliente) {

        // Si por algún motivo no se pudo construir el árbol (texto inválido)
        if (arbolSintactico == null || cliente == null) return 0.0;

        return arbolSintactico.evaluar(cliente);
    }

    // Método que devuelve la fórmula legible para mostrarla pero sin paréntesis de más
    public String obtenerFormulaLimpia() {

        // Si hay árbol, lo recorremos (inorden) para reconstruir el texto
        // Si no hay árbol (fórmula inválida), devolvemos texto vacío en vez de null.
        return arbolSintactico != null ? arbolSintactico.aTextoSinParentesisRedundantes(0) : "";
    }

    // Getter  para poder identificaresta fórmula desde afuera
    public String getId() {
        return id;
    }
}