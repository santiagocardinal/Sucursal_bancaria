package com.example.Caja_de_Herramientas.Lista;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaArray<T> implements TDALista<T> {

    private static final int CAPACIDAD_INICIAL = 10;

    private Object[] datos; // array interno donde se guardan los elementos
    private int cantidad;   // cantidad de elementos realmente ocupados

    public ListaArray() {
        datos = new Object[CAPACIDAD_INICIAL];
        cantidad = 0;
    }

    @Override
    public void agregar(T elem) {
        if (cantidad == datos.length) {
            int capacidadAnterior = datos.length;

            // el array se llenó, armamos uno nuevo del doble de tamaño
            Object[] nuevoArray = new Object[datos.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevoArray[i] = datos[i];
            }
            datos = nuevoArray;

            System.out.println("Capacidad del array aumentada de " + capacidadAnterior + " a " + datos.length);
        }

        datos[cantidad] = elem; // el nuevo elemento va al final
        cantidad++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > cantidad) throw new IndexOutOfBoundsException("Índice: " + index);
        if (elem == null) throw new IllegalArgumentException("No se permiten null");

        if (cantidad == datos.length) {
            int capacidadAnterior = datos.length;

            Object[] nuevoArray = new Object[datos.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevoArray[i] = datos[i];
            }
            datos = nuevoArray;

            System.out.println("Capacidad del array aumentada de " + capacidadAnterior + " a " + datos.length);
        }

        // corremos los elementos desde el final hasta 'index' un lugar
        // hacia la derecha, para dejar el hueco libre en 'index'
        for (int i = cantidad; i > index; i--) {
            datos[i] = datos[i - 1];
        }

        datos[index] = elem;
        cantidad++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= cantidad) return null; // mismo criterio que en las otras listas

        return (T) datos[index];
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= cantidad) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        T dato = (T) datos[index];

        // corremos los elementos posteriores un lugar hacia la izquierda,
        // para tapar el hueco que deja el elemento removido
        for (int i = index; i < cantidad - 1; i++) {
            datos[i] = datos[i + 1];
        }

        datos[cantidad - 1] = null;
        cantidad--;

        return dato;
    }

    @Override
    public boolean remover(T elem) {
        if (elem == null) return false;

        int indice = indiceDe(elem);
        if (indice == -1) return false;

        remover(indice);
        return true;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    //@SuppressWarnings("unchecked")
    public int indiceDe(T elem) {
        if (elem == null) return -1;

        for (int i = 0; i < cantidad; i++) {
            if (((T) datos[i]).equals(elem)) return i;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        if (criterio == null) return null;

        for (int i = 0; i < cantidad; i++) {
            T dato = (T) datos[i];
            if (criterio.test(dato)) return dato;
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaArray<T> resultado = new ListaArray<>();

        for (int i = 0; i < cantidad; i++) {
            resultado.agregar((T) datos[i]);
        }

        for (int i = 0; i < resultado.cantidad; i++) {
            int indiceMenor = i;

            for (int j = i + 1; j < resultado.cantidad; j++) {
                T actualJ = (T) resultado.datos[j];
                T actualMenor = (T) resultado.datos[indiceMenor];
                if (comparator.compare(actualJ, actualMenor) < 0) {
                    indiceMenor = j;
                }
            }

            Object aux = resultado.datos[i];
            resultado.datos[i] = resultado.datos[indiceMenor];
            resultado.datos[indiceMenor] = aux;
        }

        return resultado;
    }

    @Override
    public int tamano() {
        return cantidad;
    }

    @Override
    public boolean esVacio() {
        return cantidad == 0;
    }

    @Override
    public void vaciar() {
        datos = new Object[CAPACIDAD_INICIAL];
        cantidad = 0;
    }
}