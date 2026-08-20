package com.example.Caja_de_Herramientas.Lista;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaEnlazada<T> implements TDALista<T> {

    private Nodo<T> cabeza;

    public ListaEnlazada() {
        this.cabeza = null;
    }

    @Override
    public void agregar(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }

        Nodo<T> nuevo = new Nodo<>(elem);

        if (cabeza == null) {
            cabeza = nuevo;
            return;
        }

        Nodo<T> actual = cabeza;

        while (actual.getSiguiente() != null) {
            actual = actual.getSiguiente();
        }

        actual.setSiguiente(nuevo);
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }

        Nodo<T> nuevo = new Nodo<>(elem);

        // Insertar al comienzo
        if (index == 0) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
            return;
        }

        Nodo<T> actual = cabeza;
        int i = 0;

        // Buscamos el nodo anterior a la posición de inserción
        while (actual != null && i < index - 1) {
            actual = actual.getSiguiente();
            i++;
        }

        // Si no existe el nodo anterior, el índice está fuera de rango
        if (actual == null) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }

    @Override
    public T obtener(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        Nodo<T> actual = cabeza;
        int i = 0;

        while (actual != null) {
            if (i == index) {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
            i++;
        }

        throw new IndexOutOfBoundsException("Índice: " + index);
    }

    @Override
    public T remover(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        if (cabeza == null) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        // Eliminar cabeza
        if (index == 0) {
            T dato = cabeza.getDato();

            Nodo<T> eliminado = cabeza;
            cabeza = cabeza.getSiguiente();

            eliminado.setSiguiente(null);

            return dato;
        }

        Nodo<T> anterior = cabeza;
        int i = 0;

        // Llegamos al nodo anterior al que queremos eliminar
        while (anterior != null && i < index - 1) {
            anterior = anterior.getSiguiente();
            i++;
        }

        if (anterior == null || anterior.getSiguiente() == null) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        Nodo<T> eliminado = anterior.getSiguiente();

        anterior.setSiguiente(eliminado.getSiguiente());
        eliminado.setSiguiente(null);

        return eliminado.getDato();
    }

    @Override
    public boolean remover(T elem) {
        if (elem == null || cabeza == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        while (actual != null) {

            if (actual.getDato().equals(elem)) {

                // Estamos eliminando la cabeza
                if (anterior == null) {
                    cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                actual.setSiguiente(null);

                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        if (elem == null) {
            return -1;
        }

        Nodo<T> actual = cabeza;
        int indice = 0;

        while (actual != null) {

            if (actual.getDato().equals(elem)) {
                return indice;
            }

            actual = actual.getSiguiente();
            indice++;
        }

        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("El criterio no puede ser null");
        }

        Nodo<T> actual = cabeza;

        while (actual != null) {

            if (criterio.test(actual.getDato())) {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("El comparator no puede ser null");
        }

        // Creamos una copia para no modificar la lista original
        ListaEnlazada<T> resultado = new ListaEnlazada<>();

        Nodo<T> actual = cabeza;

        while (actual != null) {
            resultado.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }

        // Selection Sort
        Nodo<T> i = resultado.cabeza;

        while (i != null) {

            Nodo<T> menor = i;
            Nodo<T> j = i.getSiguiente();

            while (j != null) {

                if (comparator.compare(j.getDato(), menor.getDato()) < 0) {
                    menor = j;
                }

                j = j.getSiguiente();
            }

            // Solo intercambiamos si encontramos otro nodo menor
            if (menor != i) {
                T aux = i.getDato();
                i.setDato(menor.getDato());
                menor.setDato(aux);
            }

            i = i.getSiguiente();
        }

        return resultado;
    }

    @Override
    public int tamano() {
        int contador = 0;

        Nodo<T> actual = cabeza;

        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }

        return contador;
    }

    @Override
    public boolean esVacio() {
        return cabeza == null;
    }

    @Override
    public void vaciar() {
        cabeza = null;
    }

    public void insertarOrdenado(T elem, Comparator<T> comparator) {

        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("El comparator no puede ser null");
        }

        Nodo<T> nuevo = new Nodo<>(elem);

        // Lista vacía o elemento menor que la cabeza
        if (cabeza == null || comparator.compare(elem, cabeza.getDato()) < 0) {

            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;

            return;
        }

        Nodo<T> actual = cabeza;

        // Avanzamos hasta encontrar dónde insertar
        while (actual.getSiguiente() != null && comparator.compare(actual.getSiguiente().getDato(), elem) <= 0) {
            actual = actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
    }
}