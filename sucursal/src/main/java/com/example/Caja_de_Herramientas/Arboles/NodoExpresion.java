package com.example.Caja_de_Herramientas.Arboles;

import com.Entidades.Cliente;

public interface NodoExpresion {
    double evaluar(Cliente cliente);
    String aTextoSinParentesisRedundantes(int precedenciaPadre);
    int getPrecedencia();
}