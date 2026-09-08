package com.Entidades;

import com.example.Caja_de_Herramientas.Arboles.EvaluadorExpresion;
import com.example.Caja_de_Herramientas.Arboles.NodoExpresion;

public class FormulaComision {
    private String id;
    private String expresionOriginal;
    private NodoExpresion arbolSintactico;

    public FormulaComision(String id, String expresionOriginal) {
        this.id = id;
        this.expresionOriginal = expresionOriginal;
        this.arbolSintactico = EvaluadorExpresion.construirArbol(expresionOriginal);
    }

    public double calcularParaCliente(Cliente cliente) {
        if (arbolSintactico == null || cliente == null) return 0.0;
        return arbolSintactico.evaluar(cliente);
    }

    public String obtenerFormulaLimpia() {
        return arbolSintactico != null ? arbolSintactico.aTextoSinParentesisRedundantes(0) : "";
    }

    public String getId() {
        return id;
    }
}