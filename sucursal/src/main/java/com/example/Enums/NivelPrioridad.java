package com.example.Enums;

public enum NivelPrioridad 
{
    URGENTE(1),
    TURNO_PREVIO(2),
    NORMAL(3);

    private final int valor;

    NivelPrioridad(int valor) 
    {
        this.valor = valor;
    }

    public int getValor() 
    {
        return valor;
    }
    
}
