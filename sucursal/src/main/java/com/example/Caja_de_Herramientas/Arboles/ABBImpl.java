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
        // ─── Rango ───────────────────────────────────────────────────────
    // AVLImpl extiende esta clase y no necesita sobreescribir este método:
    // es una consulta de solo lectura sobre la forma actual del árbol, y
    // el AVL mantiene siempre la invariante de ABB (izquierda < nodo 
    // derecha), así que la poda de ramas es válida sin importar qué tan
    // balanceado esté.
    @Override
    public TDALista<T> enRango(Comparable<T> minimo, Comparable<T> maximo) {
        TDALista<T> resultado = new ListaEnlazada<>();
        if (raiz != null) {
            raiz.enRango(minimo, maximo, resultado);
        }
        return resultado;
    }
        // ─── Predecesor / Sucesor ──────────────────────────────────────────
    // No hace falta ningún método nuevo en TDAElemento/Elemento/ElementoABBImpl:
    // alcanza con "caminar" desde la raíz usando los getters que ya existen
    // (getHijoIzquierdo/getHijoDerecho/getDato), sin recorrer el árbol
    // completo. En cada nodo se decide un único camino a seguir (izquierda o
    // derecha) según la comparación contra "criterio", igual que insertar()
    // o buscar(): por eso el costo es O(altura) = O(log n) en un árbol
    // balanceado, en vez de O(n) como sería revisar cliente por cliente en
    // una lista para encontrar el más cercano.

    // Recorre el árbol comparando "criterio" contra cada nodo. Cada vez que
    // encuentra un nodo estrictamente menor que "criterio" lo guarda como
    // mejor candidato hasta el momento y sigue bajando a la derecha (busca
    // algo todavía más grande, pero que siga siendo menor que criterio); si
    // el nodo no sirve (es mayor o igual), baja a la izquierda sin
    // guardarlo. Al llegar a null, "mejor" es el predecesor.
    @Override
    public T predecesor(Comparable<T> criterio) {
        TDAElemento<T> actual = raiz;
        T mejor = null;

        while (actual != null) {
            if (criterio.compareTo(actual.getDato()) > 0) {
                mejor = actual.getDato();
                actual = actual.getHijoDerecho();
            } else {
                actual = actual.getHijoIzquierdo();
            }
        }

        return mejor;
    }

    // Simétrico a predecesor(): guarda el nodo como candidato cuando es
    // estrictamente mayor que "criterio" y sigue bajando a la izquierda
    // (busca algo más chico, pero que siga siendo mayor que criterio); si
    // no sirve, baja a la derecha. Al terminar, "mejor" es el sucesor.
    @Override
    public T sucesor(Comparable<T> criterio) {
        TDAElemento<T> actual = raiz;
        T mejor = null;

        while (actual != null) {
            if (criterio.compareTo(actual.getDato()) < 0) {
                mejor = actual.getDato();
                actual = actual.getHijoIzquierdo();
            } else {
                actual = actual.getHijoDerecho();
            }
        }

        return mejor;
    }
}