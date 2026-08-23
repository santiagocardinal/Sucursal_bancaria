package com.Entidades;

import com.example.Enums.TipoInteraccion;

public class ConteoInteraccion 
{
    private final TipoInteraccion tipo;
    private int cantidad;

    public ConteoInteraccion(TipoInteraccion tipo) {
        this.tipo = tipo;
        this.cantidad = 1;
    }

    public TipoInteraccion getTipo() {
        return tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void incrementar() {
        cantidad++;
    }
}
