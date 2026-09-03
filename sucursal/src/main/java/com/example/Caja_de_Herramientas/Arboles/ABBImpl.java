package com.example.Caja_de_Herramientas.Arboles;
import com.example.Caja_de_Herramientas.Lista.*;
import java.util.function.Consumer;

public class ABBImpl<T> implements TDAArbolBinario<T>{
    protected TDAElemento<T> raiz;

    @Override
    public TDAElemento<T> obtenerRaiz() {
        return this.raiz;
    }

    @Override
    public T buscar (Comparable<T> criterioBusqueda){
        if(raiz!= null){
            TDAElemento<T> resultado = raiz.buscar(criterioBusqueda);
            if (resultado == null) return null;
            return resultado.getDato();
        }
        return null;
    }

    // ─── Insertar ────────────────────────────────────────────────────

    @Override
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

    // Imprimir inOrden
    
    public String imprimirInOrden(){
        StringBuilder sb = new StringBuilder();
        if(this.raiz == null){
            sb.append("Árbol vacío");
        }
        else{
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
    //===============================NUEVO===================================
        /**
     * Recorre el árbol y devuelve una lista con todos los nodos que tienen
     * ambos hijos no nulos (hijo izquierdo y derecho presentes).
     * Si el árbol está vacío, retorna una lista vacía.
     */
    /*@Override
    public TDALista<TDAElemento<T>> completos() {
        if (raiz == null) return new ListaEnlazada<>();
        return raiz.completos();
    }

    /**
     * Devuelve una lista con todos los nodos que se encuentran en el nivel
     * indicado del árbol, donde el nivel 0 corresponde a la raíz.
     * Si el árbol está vacío o el nivel no existe, retorna una lista vacía.
     */
    /*@Override
    public TDALista<TDAElemento<T>> enNivel(int nivel) {
        if (raiz == null) return new ListaEnlazada<>();
        return raiz.enNivel(nivel);
    }*/
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
/*
@Override
public TDALista<TDAElemento<T>> enNivel(int nivel) {
    TDALista<TDAElemento<T>> lista = new ListaEnlazada<>();
    if (raiz != null) {
        raiz.enNivel(nivel, lista);
    }
    return lista;
} */

