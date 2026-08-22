package com.Entidades;

import java.util.Objects;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;

public class Cliente 
{
    private final String ci;
    private ListaEnlazada<IProducto> productos;
    private int numeroTurno;

    public Cliente(String ci)
    {
        this.ci = Objects.requireNonNull(ci, "El ci no puede ser nulo");
        this.productos = new ListaEnlazada<>();
    }

    public String getCi()
    {
        return ci;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno){
        this.numeroTurno = numeroTurno;
    }

    public void agregarProducto(IProducto producto){
        productos.agregar(producto);
    }

    public IProducto obtenerProducto(String id){
        return productos.buscar(producto -> producto.getId().equals(id));
    }
    
    public ListaEnlazada<IProducto> obtenerProductos(){
        return productos;
    }

    public boolean quitarProducto(String id){
        IProducto producto = obtenerProducto(id);
        return productos.remover(producto);
    }
}
