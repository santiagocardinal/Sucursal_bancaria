package com.Entidades;

import java.util.Objects;
import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;

// Clase base abstracta para todo IProducto.
//
// Antes, Cuenta/Prestamo/TarjetaDeCredito repetían cada una el mismo código
// para id/estado (getId, getEstado, modificarEstado, estaVencido)
public abstract class ProductoBase implements IProducto
{
    private final String id;
    private EstadoProducto estado;
    private Moneda moneda;
    private final TDALista<IProducto> componentes;

    //Se valida y guarda el id, arranca en estado ACTIVO, y arranca sin componentes (como hoja) hasta que se le agregue alguno.
    protected ProductoBase(String id, Moneda moneda)
    {
        this.moneda = Objects.requireNonNull(moneda);
        this.id = Objects.requireNonNull(id, "id"); // sin id no se podría localizar el producto
        this.estado = EstadoProducto.ACTIVO; // todo producto nuevo arranca ACTIVO
        this.componentes = new ListaEnlazada<>();
    }

    // Devuelve el id de este producto.
    @Override
    public String getId()
    {
        return id;
    }

    // Devuelve el estado actual del producto (ACTIVO, VENCIDO, CANCELADO, INACTIVO).
    @Override
    public EstadoProducto getEstado()
    {
        return estado;
    }

    // Cambia el estado del producto. 
    @Override
    public void modificarEstado(EstadoProducto nuevoEstado)
    {
        this.estado = Objects.requireNonNull(nuevoEstado, "nuevoEstado");
    }

    // Indica si el producto está vencido (según su propio estado, sin mirar sus componentes).
    @Override
    public boolean estaVencido()
    {
        return estado == EstadoProducto.VENCIDO;
    }

    // Agrega un componente (hijo) a este producto.Complejidad O(1) amortizado: solo se agrega al final de la lista de hijos de ESTE nodo puntual, no se recorre ni se toca el resto del árbol.
    @Override
    public void agregarComponente(IProducto componente)
    {
        if (componente == null)
        {
            throw new IllegalArgumentException("El componente no puede ser nulo");
        }
        componentes.agregar(componente);
    }

    // Devuelve los componentes directos de este producto. Se devuelve la lista real (no una copia) para poder recorrerla desde afuera sin costo extra; si el producto es una hoja, viene vacía.
    @Override
    public TDALista<IProducto> obtenerComponentes()
    {
        return componentes;
    }

    @Override
    public boolean esHoja()
    {
        return componentes.esVacio();
    }


    @Override
    public void recorrer(Consumer<IProducto> accion)
    {
        if (accion == null)
        {
            throw new IllegalArgumentException("La accion no puede ser nula");
        }

        accion.accept(this); // primero se visita este mismo producto

        int indice = 0;
        while (indice < componentes.tamano())
        {
            IProducto hijo = componentes.obtener(indice);
            hijo.recorrer(accion); // y después, recursivamente, cada componente
            indice++;
        }
    }

    public Moneda getMoneda() {
        return this.moneda;
    }

    @Override
    public boolean quitarComponente(String id) {

    if (id == null) {
        return false;
    }

    int indice = 0;

    while (indice < componentes.tamano()) {

        IProducto componente = componentes.obtener(indice);

        if (componente.getId().equals(id)) {
            componentes.remover(indice);
            return true;
        }

        if (componente.quitarComponente(id)) {
            return true;
        }

        indice++;
    }

    return false;
}

}