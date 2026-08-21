package com.example.Caja_de_Herramientas.Cola;

import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Enums.NivelPrioridad;
/**
 * Define un Tipo de Dato Abstracto (TDA) Cola genérica.
 *
 * <p>Una cola es una estructura de datos lineal que sigue la política
 * FIFO ({@code First In, First Out}), es decir, el primer elemento en ingresar
 * es el primero en salir.</p>
 *
 * <p>Esta interfaz extiende {@link TDALista}, por lo que además hereda las
 * operaciones generales de una lista. Sin embargo, las operaciones propias
 * de la cola modelan el acceso restringido a sus extremos: inserción al final
 * y extracción desde el frente.</p>
 *
 * @param <T> el tipo de los elementos almacenados en la cola
 */
public interface TDAColaPrioridad<T> extends TDACola<T> {

    
    /**
     * Inserta un elemento al final de la cola.
     *
     * @param dato el elemento a insertar
     * @return {@code true} si el elemento fue agregado correctamente;
     *         {@code false} en caso contrario
     */
    boolean poneEnCola(T dato);

    boolean poneEnCola(T dato, NivelPrioridad prioridad);

}
    

