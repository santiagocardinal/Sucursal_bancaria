package com.example.Caja_de_Herramientas.Arboles;

import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;
/**
 * Define un Tipo de Dato Abstracto (TDA) Árbol Binario genérico.
 *
 * <p>Un árbol binario es una estructura de datos jerárquica en la que cada nodo
 * puede tener como máximo dos hijos: un hijo izquierdo y un hijo derecho.</p>
 *
 * <p>Esta interfaz proporciona operaciones para insertar, buscar, eliminar elementos,
 * así como diferentes formas de recorrido del árbol (in-order, pre-order, post-order)
 * y métodos para obtener información sobre la estructura del árbol.</p>
 *
 * @param <T> el tipo de los elementos almacenados en el árbol
 */
public interface TDAArbolBinario<T> {
    /**
     * Busca y retorna el primer elemento que cumple con el predicado dado.
     *
     * @param predicate el predicado que define el criterio de búsqueda
     * @return el primer elemento que cumple el criterio, o {@code null}
     * si no existe ninguno
     */
    T buscar(Comparable<T> predicate);

    /**
     * Retorna el elemento raíz del árbol.
     *
     * @return el elemento raíz del árbol, o {@code null} si el árbol está vacío
     */
    TDAElemento<T> obtenerRaiz();

    /**
     * Elimina el o los nodos según el criterio de búsqueda.
     *
     * @param criterioBusqueda el predicado que define qué elementos deben ser eliminados
     * @return {@code true} si al menos un elemento fue eliminado;
     * {@code false} en caso contrario
     */
    boolean eliminar(Comparable<T> criterioBusqueda);

    /**
     * Agrega un dato al árbol.
     *
     * <p>Si el dato ya existe en el árbol, no se agrega nuevamente.</p>
     *
     * @param dato el elemento a insertar
     * @return {@code true} si el elemento fue agregado correctamente;
     * {@code false} si el elemento ya existía y no fue agregado
     */
    boolean insertar(Comparable<T> dato);

    void inOrder(Consumer<T> consumidor);

    void preOrder(Consumer<T> consumidor);

    void postOrder(Consumer<T> consumidor);

    boolean esVacio();

    int cantidadNodos();

    int cantidadHojas();

    int cantidadNodosInternos();

    int altura();

    // FIX: estaban comentados, pero ABBImpl ya los implementa con @Override
    // (necesita que existan acá para compilar). Se descomentaron.

    /**
     * Devuelve una lista con todos los nodos del árbol que tienen
     * ambos hijos no nulos (hijo izquierdo y derecho presentes).
     */
    TDALista<TDAElemento<T>> completos();

    /**
     * Devuelve una lista con todos los nodos que se encuentran
     * en el nivel indicado, donde el nivel 0 es la raíz.
     */
    TDALista<TDAElemento<T>> enNivel(int nivel);
        /**
     * Devuelve, ordenados por clave, todos los elementos del árbol cuya
     * clave está dentro de [minimo, maximo] (ambos límites incluidos).
     * Poda las ramas que, por la invariante del árbol, no pueden contener
     * ningún valor del rango, en vez de recorrer el árbol completo.
     */
    TDALista<T> enRango(Comparable<T> minimo, Comparable<T> maximo);
        /**
     * Devuelve el elemento del árbol con la mayor clave que sea
     * ESTRICTAMENTE menor que "criterio" (exista o no "criterio" en el
     * árbol). Devuelve null si no hay ningún elemento menor.
     */
    T predecesor(Comparable<T> criterio);

    /**
     * Devuelve el elemento del árbol con la menor clave que sea
     * ESTRICTAMENTE mayor que "criterio" (exista o no "criterio" en el
     * árbol). Devuelve null si no hay ningún elemento mayor.
     */
    T sucesor(Comparable<T> criterio);
}