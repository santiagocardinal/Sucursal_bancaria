package com.Entidades;

import com.example.Enums.Moneda;

// Acá solo queda lo específico de una cuenta: el saldo.
public class Cuenta extends ProductoBase {

    private double saldo;

    public Cuenta(String id, double saldoInicial, Moneda moneda) {
        super(id, moneda); //el numero de identificacion del producto no puede der nulo sino no podria ser localizable; lo valida ProductoBase
        this.saldo = saldoInicial;// el saldo con el que el que se empieza a poder utilizar esa cuenta
    }

    // Deposita un monto positivo en la cuenta, aumentando el saldo.
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto a depositar debe ser positivo mayor a 0");
        }
        this.saldo += monto;
    }

    // Intenta retirar un monto en efectivo. Devuelve false (sin modificar el
    // saldo) si el monto es inválido o si no alcanza el saldo disponible.
    public boolean retirarEfectivo(int monto) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }
        this.saldo -= monto;
        return true;
    }

    // Devuelve el saldo actual de la cuenta.
    public double getSaldo() {
        return saldo; //devuelbe el saldo
    }

    @Override
    public float getPrecioAPagar() {
        return 0.0f;// le pongo 0 flotante porque este metodo se usa para calcular la el total a pagar de un producto y en cuento no hay nada a pagar
    }
}