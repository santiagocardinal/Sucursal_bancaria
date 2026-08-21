package com.example.Caja_de_Herramientas.Lista;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaCircularDoble<T> implements TDALista<T> {

    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola; // último nodo, mismo criterio que en ListaDoblementeEnlazada
    private int tamano;

    public ListaCircularDoble() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    @Override
    public void agregar(T elem) {
        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }
        NodoDoble<T> nuevo = new NodoDoble<>(elem);

        if (cabeza == null) {
            // lista vacía: el nuevo nodo se apunta a sí mismo en ambas direcciones
            cabeza = nuevo;
            cola = nuevo;
            nuevo.setSiguiente(nuevo);
            nuevo.setAnterior(nuevo);
        } else {
            nuevo.setAnterior(cola);
            nuevo.setSiguiente(cabeza);
            cola.setSiguiente(nuevo);
            cabeza.setAnterior(nuevo);
            cola = nuevo;
        }

        tamano++;
    }

    @Override
    public void agregar(int index, T elem) {

        if (index < 0 || index > tamano) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }

        if (index == tamano) {
            agregar(elem); // caso "insertar al final", ya resuelto arriba
            return;
        }

        NodoDoble<T> nuevo = new NodoDoble<>(elem);

        if (index == 0) {

            nuevo.setSiguiente(cabeza);
            nuevo.setAnterior(cola);

            cabeza.setAnterior(nuevo);
            cola.setSiguiente(nuevo);

            cabeza = nuevo;

        } else {
            // ubicamos el nodo que hoy ocupa 'index': el nuevo se inserta justo antes
            NodoDoble<T> actual = cabeza;
            for (int i = 0; i < index; i++) {
                actual = (NodoDoble<T>) actual.getSiguiente();
            }

            NodoDoble<T> anteriorNodo =
                    (NodoDoble<T>) actual.getAnterior();

            nuevo.setSiguiente(actual);
            nuevo.setAnterior(anteriorNodo);
            anteriorNodo.setSiguiente(nuevo);
            actual.setAnterior(nuevo);
        }

        tamano++;
    }

    @Override
    public T obtener(int index) {

        if (index < 0 || index >= tamano) {

            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = (NodoDoble<T>) actual.getSiguiente();
        }
        return actual.getDato();
    }

    // Auxiliar privado: desconecta un nodo YA localizado, actualizando
    // cabeza, cola y 'tamano'. Como cada nodo ya sabe quién es su
    // anterior, no hace falta ir arrastrando un puntero mientras
    // recorremos, como sí había que hacer en la lista circular simple.
    private T desconectar(NodoDoble<T> nodo) {
        T dato = nodo.getDato();

        if (tamano == 1) {
            // único elemento: la lista queda vacía
            cabeza = null;
            cola = null;
        } else {

            NodoDoble<T> anteriorNodo =
                    (NodoDoble<T>) nodo.getAnterior();

            NodoDoble<T> siguienteNodo =
                    (NodoDoble<T>) nodo.getSiguiente();

            anteriorNodo.setSiguiente(siguienteNodo);
            siguienteNodo.setAnterior(anteriorNodo);

            if (nodo == cabeza) {
                cabeza = siguienteNodo;
            }

            if (nodo == cola) {
                cola = anteriorNodo;
            }
        }
        nodo.setSiguiente(null);
        nodo.setAnterior(null);

        tamano--;
        return dato;
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamano) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = (NodoDoble<T>) actual.getSiguiente();
        }

        return desconectar(actual);
    }

    @Override
    public boolean remover(T elem) {

        if (cabeza == null || elem == null) {
            return false;
        }

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (actual.getDato().equals(elem)) {
                desconectar(actual);
                return true;
            }
            actual = (NodoDoble<T>) actual.getSiguiente();
        }
        return false;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {

        if (elem == null || cabeza == null) {
            return -1;
        }

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {

            if (actual.getDato().equals(elem)) {
                return i;
            }

            actual = (NodoDoble<T>) actual.getSiguiente();
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {

        if (criterio == null) {

            throw new IllegalArgumentException(
                "El criterio no puede ser null"
            );
        }

        if (cabeza == null) {
            return null;
        }

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {

            if (criterio.test(actual.getDato())) {

                return actual.getDato();
            }

            actual = (NodoDoble<T>) actual.getSiguiente();
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {

        if (comparator == null) {

            throw new IllegalArgumentException(
                "El comparator no puede ser null"
            );
        }

        ListaCircularDoble<T> resultado =
                new ListaCircularDoble<>();

        NodoDoble<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            resultado.agregar(actual.getDato());
            actual = (NodoDoble<T>) actual.getSiguiente();
        }

        if (resultado.cabeza == null) {
            return resultado;
        }

        NodoDoble<T> i = resultado.cabeza;
        for (int pos = 0; pos < resultado.tamano; pos++) {
            NodoDoble<T> menor = i;

            NodoDoble<T> j =
                    (NodoDoble<T>) i.getSiguiente();

            for (int k = pos + 1; k < resultado.tamano; k++) {

                if (comparator.compare(
                        j.getDato(),
                        menor.getDato()) < 0) {

                    menor = j;
                }
                j = (NodoDoble<T>) j.getSiguiente();
            }

            if (menor != i) {

                T aux = i.getDato();

                i.setDato(menor.getDato());
                menor.setDato(aux);
            }

            i = (NodoDoble<T>) i.getSiguiente();
        }

        return resultado;
    }

    @Override
    public int tamano() {
        return tamano;
    }

    @Override
    public boolean esVacio() {
        return cabeza == null;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }
}