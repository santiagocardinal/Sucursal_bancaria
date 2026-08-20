package com.Entidades;
//src\main\java\com\example\Enums\EstadoProducto.java
import com.example.Enums.EstadoProducto;

import com.example.Enums.EstadoProducto;

/**
 * Contrato común de todo producto bancario. Es interfaz (no clase
 * abstracta) porque así lo tenían dibujado: Cliente guarda una
 * ListaEnlazada&lt;IProducto&gt; homogénea sin importarle si adentro hay
 * una Cuenta, un Prestamo o una TarjetaDeCredito — esa es la Abstracción
 * que permite recorrer productos de un cliente de forma genérica.
 *
 * id y estado figuraban como "atributos" de la interfaz en el diagrama,
 * pero una interfaz de Java no puede tener campos de instancia: los
 * traduzco a getId()/getEstado(), y cada clase concreta guarda sus propios
 * campos privados para responderlos.
 */
public interface IProducto {

    String getId();

    EstadoProducto getEstado();

    /**
     * El diagrama la tenía como "modificarEstado(): void", sin parámetro.
     * Sin saber a qué estado cambia, el método no puede hacer nada útil:
     * le agrego el nuevo estado como argumento.
     */
    void modificarEstado(EstadoProducto nuevoEstado);

    boolean estaVencido();
}