package com.Entidades;
import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.TDALista;
//src\main\java\com\example\Enums\EstadoProducto.java
import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;


public interface IProducto //Dice el que se debe hacer no el como!
{

    // Necesario para poder BUSCAR un producto puntual dentro de una lista
    // mezclada de productos.
    String getId();
 
    //getEstado() permite leer si el estado del producto es (ACTIVO, VENCIDO, CANCELADO)
    EstadoProducto getEstado();
 
    // modificar el estado puntual de un producto utilizando los ENUMS ya dados (ACTIVO, VENCIDO, CANCELADO)
    void modificarEstado(EstadoProducto nuevoEstado);
 
    // para ver si un producto de un determinado cliente puede estar vencido o no
    boolean estaVencido();

    // ===================== PRODUCTOS COMPUESTOS (árbol general n-ario) =====================
    // A partir de acá, todo IProducto puede además actuar como nodo de un
    // árbol general: puede tener cero o más "componentes" (hijos), y esos
    // componentes pueden a su vez tener los suyos, sin límite de profundidad

    // Agrega un componente (hijo) a este producto. Si el producto no tenía componentes todavía, a partir de este llamado deja de ser una hoja.
    void agregarComponente(IProducto componente);

    // Devuelve los componentes directos de este producto (no los "nietos"). Si el producto es una hoja, devuelve una lista vacía.
    TDALista<IProducto> obtenerComponentes();

    // Indica si este producto es una hoja del árbol de composición, es decir, si no tiene ningún componente propio.
    boolean esHoja();

    // Recorre este producto y, recursivamente, todos sus componentes (sin límite de profundidad).
    void recorrer(Consumer<IProducto> accion);

    Moneda getMoneda();

    float getPrecioAPagar();
 
}