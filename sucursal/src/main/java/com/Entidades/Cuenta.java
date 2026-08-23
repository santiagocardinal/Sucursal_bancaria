package com.Entidades;
import com.example.Enums.EstadoProducto;

import java.util.Objects;

public class Cuenta implements IProducto { //extiende de la interfaz de IProducto

    private final String id;
    private EstadoProducto estado;
    private double saldo;

    public Cuenta(String id, double saldoInicial) {
        this.id = Objects.requireNonNull(id, "id"); //el numero de identificacion del producto no puede der nulo sino no podria ser localizable
        this.estado = EstadoProducto.ACTIVO;// cuando se crea una nueva cuenta esta pasa a estar ACTIVCA
        this.saldo = saldoInicial;// el saldo con el que el que se empieza a poder utilizar esa cuenta
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo mayor a 0");
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

    @Override
    public void modificarEstado(EstadoProducto nuevoEstado) {
        // El parámetro es de tipo EstadoProducto (el enum),hace que
        // en tiempo de COMPILACIÓN no se pueda pasar cualquier cosa que no sea uno de sus
        // valores válidos (ACTIVO, VENCIDO, CANCELADO, INACTIVO) 
        this.estado = Objects.requireNonNull(nuevoEstado, "nuevoEstado");
    }

    @Override
    public boolean estaVencido() {
        return estado == EstadoProducto.VENCIDO; //si el estado de la cuenta es VENCIDO devuelbe True
    }

    public double getSaldo() {
        return saldo; //devuelbe el saldo
    }
}