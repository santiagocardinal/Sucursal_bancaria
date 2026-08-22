package com.Entidades;

import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoInteraccion;

public class HistorialInteracciones {
    private Pila<Interaccion> interacciones;

    public HistorialInteracciones() {
        this.interacciones = new Pila<>();
    }

    public void registrarInteraccion(Interaccion interaccionRecibida) {
        if (interaccionRecibida == null) {
            throw new IllegalArgumentException("la interacción no puede ser nula");
        }
        this.interacciones.mete(interaccionRecibida);
    }

    public Pila<Interaccion> obtenerPorTipo(TipoInteraccion tipo) {
        return filtrar(actual -> actual.getTipo() == tipo);
    }

    public Pila<Interaccion> obtenerPorCliente(String clienteId) {
        return filtrar(actual -> actual.getClienteId().equals(clienteId));
    }

    private Pila<Interaccion> filtrar(java.util.function.Predicate<Interaccion> criterio) {
        Pila<Interaccion> pilaAuxiliar = new Pila<>();
        Pila<Interaccion> pilaResultado = new Pila<>();

        while (!this.interacciones.esVacio()) {
            Interaccion actual = this.interacciones.saca();
            pilaAuxiliar.mete(actual);
        }

        while (!pilaAuxiliar.esVacio()) {
            Interaccion actual = pilaAuxiliar.saca();
            this.interacciones.mete(actual);

            if (criterio.test(actual)) {
                pilaResultado.mete(actual);
            }
        }

        return pilaResultado;
    }
}
