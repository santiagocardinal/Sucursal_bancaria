package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.IProducto;
import com.Entidades.Sucursal;
import com.Entidades.Interaccion;
import com.Entidades.Documento;
import com.example.Enums.EstadoProducto;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;


public class AtencionEjecutivos implements IEstrategiaAtencion {

    
    private Sucursal sucursal;

    public AtencionEjecutivos(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

  
    @Override
    public void atender(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        switch (solicitud.getTipoInteraccion()) {

            case CONSULTA:
                consultar(cliente, solicitud.getIdProducto(), mostradorId);
                break;

            case BAJA_PRODUCTO:
                bajaProducto(cliente, solicitud.getIdProducto(), mostradorId);
                break;

            case MODIFICACION:
                modificarProducto(cliente, solicitud, mostradorId);
                break;

            default:
                break;
        }
    }

    // Consulta sobre cualquier producto del cliente 
    private void consultar(Cliente cliente, String idProducto, String mostradorId) {

        IProducto producto = buscarProducto(cliente, idProducto);

        // Si el cliente no tiene ningún producto con ese id, no hay nada
        // que consultar, se corta todo
        if (producto == null) {
            return;
        }

        // Se registra la interacción para la auditoría.
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    // Da de baja un producto del cliente (lo cancela).
    private void bajaProducto(Cliente cliente, String idProducto, String mostradorId) {

        IProducto producto = buscarProducto(cliente, idProducto);

        // Si no existe el producto, no hay nada que dar de baja.
        if (producto == null) {
            return;
        }

        // Se cambia el estado del producto a CANCELADO. 
        producto.modificarEstado(EstadoProducto.CANCELADO);

        // Se registran dos cosas por la baja:
        // 1) la interacción, para la auditoría.
        Interaccion interaccion = new Interaccion(TipoInteraccion.BAJA_PRODUCTO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);

        // 2) un comprobante de baja, como Documento nuevo
        Documento documento = new Documento(generarId(), TipoDocumento.COMPROBANTE_BAJA, cliente, java.time.LocalDate.now(), null);
        sucursal.registrarDocumento(documento);
    }

    // Modifica un producto existente del cliente. En este código, la única modificación que hace es reactivarlo (ponerlo en ACTIVO) 
    private void modificarProducto(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        IProducto producto = buscarProducto(cliente, solicitud.getIdProducto());

        // Si no existe el producto, no hay nada que modificar, se corta ahi
        if (producto == null) {
            return;
        }
        // Se fuerza el estado a ACTIVO 
        producto.modificarEstado(EstadoProducto.ACTIVO);
        // Se registra la interacción de auditoria
        Interaccion interaccion = new Interaccion(TipoInteraccion.MODIFICACION, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    // Busca, entre TODOS los productos del cliente, el que tenga el id pedido
    private IProducto buscarProducto(Cliente cliente, String idProducto) {

        int indice = 0;

        while (indice < cliente.obtenerProductos().tamano()) {
            IProducto producto = cliente.obtenerProductos().obtener(indice);
            indice++;

            if (producto.getId().equals(idProducto)) {
                return producto;
            }
        }
        return null;
    }

    // Genera un id único para documentos nuevos, usando UUID 
    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}