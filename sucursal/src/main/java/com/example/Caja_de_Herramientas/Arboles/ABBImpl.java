package com.example.Caja_de_Herramientas.Arboles;
import com.example.Caja_de_Herramientas.Lista.*;
import java.util.function.Consumer;

// FIX: se sacó el "<T extends Comparable<T>>" que había aparecido en una
// versión de este archivo, junto con insertar(T dato). Volvió a
// insertar(Comparable<T> dato) como el resto de los métodos (buscar,
// eliminar, obtenerNivel), para que todo el árbol use un único criterio
// consistente y no dependa de que T declare esa cota genérica.
public class ABBImpl<T> implements TDAArbolBinario<T> {
    protected TDAElemento<T> raiz;

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return this.raiz;
    }

    @Override
    public T buscar(Comparable<T> criterioBusqueda) {
        if (raiz != null) {
            TDAElemento<T> resultado = raiz.buscar(criterioBusqueda);
            if (resultado == null) return null;
            return resultado.getDato();
        }
        return null;
    }

    // ─── Insertar ────────────────────────────────────────────────────

    @Override
    @SuppressWarnings("unchecked")
    public boolean insertar(Comparable<T> dato) {
        if (raiz == null) {
            raiz = new ElementoABBImpl<>((T) dato);
            return true;
        } else {
            return raiz.insertar(dato);
        }
    }

    // ─── Eliminar ────────────────────────────────────────────────────

    @Override
    public boolean eliminar(Comparable<T> criterio) {
        if (this.raiz == null) return false;

        if (raiz.buscar(criterio) == null) return false;
        this.raiz = this.raiz.eliminar(criterio);
        return true;
    }

    // ─── Recorridos ──────────────────────────────────────────────────

    @Override
    public void inOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.inOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void preOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.preOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void postOrder(Consumer<T> consumidor) {
        if (raiz != null) {
            raiz.postOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    // ─── Utilidades ──────────────────────────────────────────────────

    @Override
    public boolean esVacio() {
        return raiz == null;
    }

    @Override
    public int cantidadNodos() {
        return (raiz == null) ? 0 : raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas() {
        return (raiz == null) ? 0 : raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos() {
        return (raiz == null) ? 0 : raiz.cantidadNodosInternos();
    }

    public String imprimirInOrden() {
        StringBuilder sb = new StringBuilder();
        if (this.raiz == null) {
            sb.append("Árbol vacío");
        } else {
            this.raiz.inOrder(nodo -> {
                sb.append(nodo.getDato());
            });
        }
        return sb.toString();
    }

    @Override
    public int altura() {
        if (raiz == null) return 0;
        return raiz.altura(); // altura de la raíz = altura del árbol
    }

    @Override
    public TDALista<TDAElemento<T>> completos() {
        TDALista<TDAElemento<T>> lista = new ListaEnlazada<>();
        if (raiz != null) {
            raiz.completos(lista);
        }
        return lista;
    }

    @Override
    public TDALista<TDAElemento<T>> enNivel(int nivel) {
        TDALista<TDAElemento<T>> lista = new ListaEnlazada<>();
        if (raiz != null) {
            raiz.enNivel(nivel, lista);
        }
        return lista;
    }
}