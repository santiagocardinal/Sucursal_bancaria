package com.Entidades;

import java.util.Objects;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;
import com.example.Enums.EstadoProducto;

public class Cliente implements Comparable<Cliente> {
    private final String ci;
    private ListaEnlazada<IProducto> productos;
    private int numeroTurno;
    private SolicitudAtencion solicitudActual;

    public Cliente(String ci) {
        this.ci = Objects.requireNonNull(ci, "El ci no puede ser nulo");
        this.productos = new ListaEnlazada<>();
    }

    public String getCi() {
        return ci;
    }

    public int getNumeroTurno() {
        return numeroTurno;
    }

    public void setNumeroTurno(int numeroTurno) {
        this.numeroTurno = numeroTurno;
    }

    public SolicitudAtencion getSolicitudActual() {
        return solicitudActual;
    }

    public void setSolicitudActual(SolicitudAtencion solicitudActual) {
        this.solicitudActual = solicitudActual;
    }

    public void agregarProducto(IProducto producto) {
        productos.agregar(producto);
    }

    public IProducto obtenerProducto(String id) {
        return productos.buscar(producto -> producto.getId().equals(id));
    }

    public ListaEnlazada<IProducto> obtenerProductos() {
        return productos;
    }

    public boolean quitarProducto(String id) {
        IProducto producto = obtenerProducto(id);
        if (producto == null) return false;
        return productos.remover(producto);
    }

    public ListaEnlazada<IProducto> obtenerProductosVencidosOCancelados() {
        ListaEnlazada<IProducto> resultado = new ListaEnlazada<>();
        int indice = 0;
        while (indice < productos.tamano()) {
            IProducto producto = productos.obtener(indice);
            if (producto.estaVencido() || producto.getEstado() == EstadoProducto.CANCELADO) {
                resultado.agregar(producto);
            }
            indice++;
        }
        return resultado;
    }

    @Override
    public int compareTo(Cliente otro) {
        return this.ci.compareTo(otro.ci);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cliente otroCliente = (Cliente) obj;
        return this.ci.equals(otroCliente.ci);
    }
    // A diferencia de obtenerProducto(id) (que
    // solo mira los productos de primer nivel), acá se usa recorrer() sobre
    // cada producto raíz para bajar por su árbol de composición completo.
    public IProducto buscarProductoEnCartera(String id) {
        IProducto[] encontrado = new IProducto[1];

        int indice = 0;
        while (indice < productos.tamano() && encontrado[0] == null) {
            IProducto raiz = productos.obtener(indice);

            raiz.recorrer(producto -> {
                if (encontrado[0] == null && producto.getId().equals(id)) {
                    encontrado[0] = producto;
                }
            });

            indice++;
        }

        return encontrado[0];
    }

    public boolean quitarProductoEnCartera(String id) {

    if (id == null) {
        return false;
    }

    int indice = 0;

    while (indice < productos.tamano()) {

        IProducto producto = productos.obtener(indice);

        if (producto.getId().equals(id)) {
            productos.remover(indice);
            return true;
        }

        if (producto.quitarComponente(id)) {
            return true;
        }

        indice++;
    }

    return false;
    }
}