package com.Entidades;

import java.util.Objects;

import com.example.Enums.Moneda;

public class TarjetaDeCredito extends ProductoBase {

    private float limite;
    private float saldo;

    public TarjetaDeCredito(String id, Moneda moneda ,float limite) {
        
        super(id, moneda);
        
        this.limite = Objects.requireNonNull(limite);
        this.saldo = Objects.requireNonNull(limite);
    }

    public void setSaldo(float nuevoSaldo){
        this.saldo = nuevoSaldo;
    }// para testear

    @Override
    public float getPrecioAPagar() {
        return limite - saldo;
    }
}