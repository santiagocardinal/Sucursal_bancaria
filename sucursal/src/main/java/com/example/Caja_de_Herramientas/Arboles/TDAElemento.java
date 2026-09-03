package com.example.Caja_de_Herramientas.Arboles;
import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;

/**
 * Modela un nodo del árbol binario (ABB / AVL).
 * La implementación de esta estructura debe ser recursiva.
 *
 * DECISIÓN GENERAL (ver el resumen que te dejo en el chat): esta interfaz
 * había terminado mezclada con la del árbol general — tenía hijoIzq/hijoDer
 * PERO TAMBIÉN insertar(T)/buscar(T)/obtenerNivel(T), lo que obligaba a
 * ElementoABBImpl a implementar 4 métodos que solo tiraban
 * UnsupportedOperationException. La separé de nuevo en dos interfaces:
 * esta (binaria, ordenada por Comparable<T>, para ABB/AVL) y
 * TElementoArbolGeneral (con lista de hijos, sin orden, para el árbol n-ario).
 *
 * FIX: getHijoIzquierdo/getHijoDerecho tenían los Javadoc cruzados
 * (el comentario de uno describía al otro) — ya corregido acá abajo.
 */
public interface TDAElemento<T> {

    /**
     * Asigna el hijo izquierdo del nodo actual. Puede ser nulo.
     */
    void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo);

    /**
     * Asigna el hijo derecho del nodo actual. Puede ser nulo.
     */
    void setHijoDerecho(TDAElemento<T> hijoDerecho);

    /**
     * Devuelve el hijo izquierdo del nodo actual. El valor es nulo si no tiene hijo izquierdo.
     */
    TDAElemento<T> getHijoIzquierdo();

    /**
     * Devuelve el hijo derecho del nodo actual. El valor es nulo si no tiene hijo derecho.
     */
    TDAElemento<T> getHijoDerecho();

    /**
     * Actualiza el dato del nodo actual.
     */
    void setDato(T dato);

    /**
     * devuelve el dato del nodo actual.
     */
    T getDato();

    /**
     * Busca un nodo por un criterio de búsqueda.
     * Si no se encuentra, retorna nulo.
     */
    TDAElemento<T> buscar(Comparable<T> criterioBusqueda);

    /**
     * Elimina un nodo del árbol según el criterio de búsqueda.
     * Si se encuentra, se retorna el nodo borrado. En otro caso retornar null.
     */
    TDAElemento<T> eliminar(Comparable<T> criterioBusqueda);

    /**
     * Agrega un nuevo elemento al árbol.
     * Si el nuevoDato existe, no se agrega.
     */
    boolean insertar(Comparable<T> nuevoDato);

    void inOrder(Consumer<TDAElemento<T>> consumidor);

    void preOrder(Consumer<TDAElemento<T>> consumidor);

    void postOrder(Consumer<TDAElemento<T>> consumidor);

    boolean esHoja();

    int cantidadHojas();

    int cantidadNodosInternos();

    int cantidadNodos();

    int altura();

    /**
     * retornar el nivel relativo del nodo que coincide con el criterio de búsqueda
     * si no se encuentra, retorna -1
     */
    int obtenerNivel(Comparable<T> criterioBusqueda);

    /**
     * Devuelve una lista con todos los nodos del subárbol con raíz en este nodo
     * que tienen ambos hijos no nulos (hijo izquierdo y derecho presentes).
     */
    void completos(TDALista<TDAElemento<T>> lista);

    /**
     * Devuelve una lista con todos los nodos del subárbol con raíz en este nodo
     * que se encuentran en el nivel indicado, donde el nivel 0 es este nodo.
     */
    void enNivel(int nivel, TDALista<TDAElemento<T>> lista);
}