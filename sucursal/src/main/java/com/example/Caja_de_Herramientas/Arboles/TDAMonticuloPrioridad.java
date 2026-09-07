package com.example.Caja_de_Herramientas.Arboles;

import com.example.Enums.NivelPrioridad;

/**
 * Define el TDA de una cola con prioridad implementada mediante un montículo (heap).
 *
 * <p>A diferencia de {@code TDAColaPrioridad} (que extiende de {@code TDACola},
 * y esta a su vez de {@code TDALista}), esta interfaz no hereda operaciones
 * de lista que no tienen sentido para un montículo — como acceso por índice,
 * búsqueda lineal u ordenamiento externo, ya que el montículo mantiene su
 * propio orden interno de forma automática.</p>
 *
 * <p>Se declara como una interfaz nueva y separada de {@code TDAColaPrioridad}
 * para no modificar las estructuras ya entregadas en el primer hito.</p>
 *
 * @param <T> el tipo de los elementos almacenados
 */
public interface TDAMonticuloPrioridad<T> {

    /**
     * Inserta un elemento con el nivel de prioridad indicado.
     *
     * @param dato el elemento a insertar
     * @param prioridad el nivel de prioridad del elemento
     * @return {@code true} si se insertó correctamente; {@code false} si
     *         el dato o la prioridad son {@code null}
     */
    boolean poneEnCola(T dato, NivelPrioridad prioridad);

    /**
     * Inserta un elemento con nivel de prioridad NORMAL por defecto.
     *
     * @param dato el elemento a insertar
     * @return {@code true} si se insertó correctamente
     */
    boolean poneEnCola(T dato);

    /**
     * Retorna el elemento más urgente sin removerlo.
     *
     * @return el elemento en la raíz del montículo
     * @throws java.util.NoSuchElementException si el montículo está vacío
     */
    T frente();

    /**
     * Remueve y retorna el elemento más urgente.
     *
     * @return el elemento removido
     * @throws java.util.NoSuchElementException si el montículo está vacío
     */
    T quitaDeCola();

    /**
     * Modifica el nivel de prioridad de un elemento que ya está esperando
     * en el montículo, reacomodando la estructura para mantener válida
     * la propiedad del heap.
     *
     * @param dato el elemento cuya prioridad se quiere modificar
     * @param nuevaPrioridad el nuevo nivel de prioridad
     * @return {@code true} si el elemento fue encontrado y su prioridad
     *         actualizada; {@code false} si no se encontró
     */
    boolean modificarPrioridad(T dato, NivelPrioridad nuevaPrioridad);

    /** Vacía por completo el montículo. */
    void anula();

    /** @return la cantidad de elementos almacenados */
    int tamano();

    /** @return {@code true} si el montículo no tiene elementos */
    boolean esVacio();

    /** Vacía por completo el montículo (alias de {@link #anula()}). */
    void vaciar();
}