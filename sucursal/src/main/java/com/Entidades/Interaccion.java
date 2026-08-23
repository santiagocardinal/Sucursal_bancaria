package com.Entidades;

import java.util.Objects;

import com.example.Enums.TipoInteraccion;

public class Interaccion {
    private final TipoInteraccion tipo;
    private final String clienteId;
    private final String mostradorId; //es la representación del empleado 

    public TipoInteraccion getTipo() {
        return tipo;
    }

    public String getClienteId()
    {
        return clienteId;
    }

    public String getMostradorId() {
        return mostradorId;
    }

    public Interaccion(TipoInteraccion tipo, String clienteId, String mostradorId) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
        this.clienteId = Objects.requireNonNull(clienteId, "El clienteId no puede ser nulo");
        this.mostradorId = Objects.requireNonNull(mostradorId, "El mostradorId no puede ser nulo");
    }
}
