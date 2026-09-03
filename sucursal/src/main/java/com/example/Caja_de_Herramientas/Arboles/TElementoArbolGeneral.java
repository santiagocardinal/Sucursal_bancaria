
package com.example.Caja_de_Herramientas.Arboles;
import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;

/**
 * Modela un nodo del árbol general (n-ario).
 * La implementación de esta estructura debe ser recursiva.
 *
 * RENOMBRADA: esta interfaz se llamaba TDAElemento, igual que la del árbol
 * binario, y las dos definiciones no podían coexistir con ese nombre en el
 * mismo paquete. Se renombra a TElementoArbolGeneral (siguiendo el mismo
 * criterio que TElementoAB para el binario) para que ambas interfaces
 * puedan convivir sin pisarse. El contenido es el mismo que ya habíamos
 * dejado: lista de hijos en vez de hijoIzq/hijoDer, y operaciones por
 * igualdad (T) en vez de por orden (Comparable<T>), porque un árbol
 * general no tiene un criterio de orden como el ABB.
 */
public interface TElementoArbolGeneral<T> {

    TDALista<TElementoArbolGeneral<T>> getHijos();

    void agregar(TElementoArbolGeneral<T> hijo);

    void setDato(T dato);

    T getDato();

    TElementoArbolGeneral<T> buscar(T criterioBusqueda);

    TElementoArbolGeneral<T> eliminar(T criterioBusqueda);

    boolean insertar(T nuevoDato);

    void preOrder(Consumer<TElementoArbolGeneral<T>> consumidor);

    void postOrder(Consumer<TElementoArbolGeneral<T>> consumidor);

    boolean esHoja();

    int cantidadHojas();

    int cantidadNodosInternos();

    int cantidadNodos();

    int altura();

    int obtenerNivel(T criterioBusqueda);

    void enNivel(int nivel, TDALista<TElementoArbolGeneral<T>> lista);
} 
