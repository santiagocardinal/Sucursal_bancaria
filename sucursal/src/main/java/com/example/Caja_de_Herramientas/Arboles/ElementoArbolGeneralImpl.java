package com.example.Caja_de_Herramientas.Arboles;

import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;

public class ElementoArbolGeneralImpl<T> implements TDAElemento<T> {

    private T dato;
    private TDALista<TDAElemento<T>> hijos;

    public ElementoArbolGeneralImpl(T dato) {
        this.dato = dato;
        this.hijos = new ListaEnlazada<>();
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public TDALista<TDAElemento<T>> getHijos() {
        return hijos;
    }

    @Override
    public void agregar(TDAElemento<T> hijo) {
        hijos.agregar(hijo);
    }

    @Override
    public boolean eliminarHijo(TDAElemento<T> hijo) {
        return hijos.remover(hijo);
    }

    @Override
    public boolean esHoja() {
        return hijos.esVacio();
    }

    // ---------- BÚSQUEDA ----------

    @Override
    public TDAElemento<T> buscar(T criterioBusqueda) {
        if (this.dato.equals(criterioBusqueda)) {
            return this;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            TDAElementoGeneral<T> encontrado = hijos.obtener(i).buscar(criterioBusqueda);
            if (encontrado != null) {
                return encontrado;
            }
        }

        return null;
    }

    // ---------- MÉTRICAS ----------

    @Override
    public int cantidadHojas() {
        if (esHoja()) {
            return 1;
        }

        int total = 0;
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadHojas();
        }
        return total;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja()) {
            return 0;
        }

        int total = 1; // este nodo es interno
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadNodosInternos();
        }
        return total;
    }

    @Override
    public int cantidadNodos() {
        int total = 1; // este nodo
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadNodos();
        }
        return total;
    }

    @Override
    public int altura() {
        if (esHoja()) {
            return 1;
        }

        int maxAltHijos = 0;
        for (int i = 0; i < hijos.tamano(); i++) {
            int altHijo = hijos.obtener(i).altura();
            if (altHijo > maxAltHijos) {
                maxAltHijos = altHijo;
            }
        }
        return 1 + maxAltHijos;
    }

    @Override
    public int obtenerNivel(T criterioBusqueda) {
        return obtenerNivelRecursivo(criterioBusqueda, 0);
    }

    private int obtenerNivelRecursivo(T criterioBusqueda, int nivelActual) {
        if (this.dato.equals(criterioBusqueda)) {
            return nivelActual;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            int nivel = ((ElementoArbolGeneralImpl<T>) hijos.obtener(i))
                    .obtenerNivelRecursivo(criterioBusqueda, nivelActual + 1);
            if (nivel != -1) {
                return nivel;
            }
        }

        return -1;
    }

    // ---------- RECORRIDOS ----------

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        consumidor.accept(this);
        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).preOrder(consumidor);
        }
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).postOrder(consumidor);
        }
        consumidor.accept(this);
    }

    @Override
    public void enNivel(int nivel, TDALista<TDAElemento<T>> lista) {
        if (nivel == 0) {
            lista.agregar(this);
            return;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).enNivel(nivel - 1, lista);
        }
    }

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHijoIzquierdo'");
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHijoDerecho'");
    }

    @Override
    public TDAElemento<T> getHijoIzquierdo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHijoIzquierdo'");
    }

    @Override
    public TDAElemento<T> getHijoDerecho() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHijoDerecho'");
    }

    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscar'");
    }

    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }

    @Override
    public boolean insertar(T nuevoDato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inOrder'");
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerNivel'");
    }

    @Override
    public void completos(TDALista<TDAElemento<T>> lista) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completos'");
    }
}