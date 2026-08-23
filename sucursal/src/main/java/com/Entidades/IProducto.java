package com.Entidades;
//src\main\java\com\example\Enums\EstadoProducto.java
import com.example.Enums.EstadoProducto;


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
 
}