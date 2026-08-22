package com.Entidades;

import java.util.Objects;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class Cliente 
{
    private final String ci;
    private ListaEnlazada<IProducto> productos;
    private int numeroTurno;
    private SolicitudAtencion solicitudActual;

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

    public SolicitudAtencion getSolicitudActual() {
        return solicitudActual;
    }

    public void setSolicitudActual(SolicitudAtencion solicitudActual) {
        this.solicitudActual = solicitudActual;
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
        
        if (producto == null) {
            return false;
        }
        
        return productos.remover(producto);
    }
}
