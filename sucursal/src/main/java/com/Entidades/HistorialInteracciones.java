package com.Entidades;

import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoInteraccion;

public class HistorialInteracciones {
    private Pila<Interaccion> interacciones;

    public HistorialInteracciones() {
        this.interacciones = new Pila<>();
    }

    public void registrarInteraccion(Interaccion interaccionRecibida) {
        this.interacciones.mete(interaccionRecibida);
    }

    public Pila<Interaccion> obtenerPorTipo(TipoInteraccion tipo) {
        Pila<Interaccion> pilaAuxiliar = new Pila<>();
        Pila<Interaccion> pilaResultado = new Pila<>();

        while (this.interacciones.esVacio() == false) {
            Interaccion actual = this.interacciones.saca();
            if (actual != null && actual.getTipo() == tipo) {
                pilaResultado.mete(actual);
            }
            pilaAuxiliar.mete(actual);

        }
        while (pilaAuxiliar.esVacio() == false) {
            Interaccion actual = pilaAuxiliar.saca();
            this.interacciones.mete(actual);
        }
        return pilaResultado;
    }

}
