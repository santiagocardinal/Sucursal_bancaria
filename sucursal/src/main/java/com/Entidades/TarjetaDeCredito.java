package com.Entidades;

import java.util.Objects;

import com.example.Enums.EstadoProducto;

public class TarjetaDeCredito implements IProducto {

    private final String id;
    private EstadoProducto estado;

    public TarjetaDeCredito(String id) {
        this.id = Objects.requireNonNull(id, "id");
        this.estado = EstadoProducto.ACTIVO;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EstadoProducto getEstado() {
        return estado;
    }

    @Override
    public void modificarEstado(EstadoProducto nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "nuevoEstado");
    }

    @Override
    public boolean estaVencido() {
        return estado == EstadoProducto.VENCIDO;
    }
}
