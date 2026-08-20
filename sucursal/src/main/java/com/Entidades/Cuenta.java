package com.Entidades;
import com.example.Enums.EstadoProducto;

import java.util.Objects;

public class Cuenta implements IProducto {

    private final String id;
    private EstadoProducto estado;
    private double saldo;

    public Cuenta(String id, double saldoInicial) {
        this.id = Objects.requireNonNull(id, "id");
        this.estado = EstadoProducto.ACTIVO;
        this.saldo = saldoInicial;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo");
        }
        this.saldo += monto;
    }

    public boolean retirarEfectivo(int monto) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }
        this.saldo -= monto;
        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EstadoProducto getEstado() {
        return estado;
    }

    //ESTO LE PEDI A CLAUDE LO DE MODIFICAR EL ESTADO USA EL ENUM
    @Override
    public void modificarEstado(EstadoProducto nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "nuevoEstado");
    }

    @Override
    public boolean estaVencido() {
        return estado == EstadoProducto.VENCIDO;
    }

    public double getSaldo() {
        return saldo;
    }
}