package com.Entidades;

import java.util.Objects;

import com.example.Enums.TipoDocumento;

public class Documento {

    private final String id;
    private final TipoDocumento tipo;
    private final Cliente cliente;

    public Documento(String id, TipoDocumento tipo, Cliente cliente) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
    }

    public String getId() {
        return id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }
}