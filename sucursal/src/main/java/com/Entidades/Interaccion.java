package com.Entidades;

import java.util.Objects;

import com.example.Enums.TipoInteraccion;

public class Interaccion {
    private final TipoInteraccion tipo;
    private final String clienteId;

    public TipoInteraccion getTipo() {
        return tipo;
    }

    public String getClienteId()
    {
        return clienteId;
    }

    public Interaccion(TipoInteraccion tipo, String clienteId) {
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
        this.clienteId = Objects.requireNonNull(clienteId, "El clienteId no puede ser nulo");
    }
}
