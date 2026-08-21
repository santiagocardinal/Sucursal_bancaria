package com.Entidades;

import com.example.Enums.TipoInteraccion;

public class Interaccion {
    private String id;
    private TipoInteraccion tipo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoInteraccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoInteraccion tipo) {
        this.tipo = tipo;
    }

    public Interaccion(String id, TipoInteraccion tipo) {
        this.id = id;
        this.tipo = tipo;
    }
}
