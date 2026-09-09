package com.Entidades;

import java.util.Objects;
import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;


// Antes, Cuenta/Prestamo/TarjetaDeCredito repetían cada una el mismo código para id/estado (getId, getEstado, modificarEstado, estaVencido)
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

    // Agrega un componente (hijo) a este producto.Complejidad O(1) solo se agrega al final de la lista de hijos de este nodo puntual, no se recorre ni se toca el resto del árbol.
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
    
    // Método que busca y elimina un componente por id, en cualquier nivel del árbol de productos 
    @Override
    public boolean quitarComponente(String id) {

        // Si no se pasó un id válido, no hay nada que buscar
        if (id == null) {
            return false;
        }

        int indice = 0;

        // Recorremos los componentes DIRECTOS de este nodo (los hijos de primer nivel, no los de segundo nivel todavía)
        while (indice < componentes.tamano()) {

            IProducto componente = componentes.obtener(indice);

            // Caso 1: el componente que estamos mirando ES el que hay que  eliminar. Se remueve directamente de la lista de este nodo y se corta la búsqueda devolviendo true.
            if (componente.getId().equals(id)) {
                componentes.remover(indice);
                return true;
            }

            // Caso 2: no es este componente, pero podría estar anidado dentro de él (si este componente es a su vez un paquete).
            // Se le delega la misma búsqueda a él mismo, de forma
            // recursiva y baja un nivel más repite el mismo proceso.
            
            if (componente.quitarComponente(id)) {
                return true;
            }

            // No estaba ni en este nivel ni anidado en este componente por lo que seguimos con el siguiente hermano
            indice++;
        }

        // Se recorrieron todos los componentes de este nivel (y sus anidados) y no apareció ningún producto con ese id
        return false;
    }

}