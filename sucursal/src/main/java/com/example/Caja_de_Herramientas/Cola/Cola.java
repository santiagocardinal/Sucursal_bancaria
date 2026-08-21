package com.example.Caja_de_Herramientas.Cola;

import java.util.NoSuchElementException;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;

public class Cola<T> extends ListaEnlazada<T> implements TDACola<T> {

    public Cola() {
        super();
    }

    @Override
    public T frente() {
        if (esVacio()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        return obtener(0);
    }

    @Override
    public boolean poneEnCola(T dato) {
        agregar(dato);
        return true;
    }


    @Override
    public T quitaDeCola() {
        if (esVacio()) {
            throw new NoSuchElementException("La cola está vacía");
        }

        return remover(0);
    }

    @Override
    public void anula() {
        vaciar();
    }
}