package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.IProducto;
import com.example.Enums.EstadoProducto;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionEjecutivos implements IEstrategiaAtencion {

    private Sucursal sucursal;

    public AtencionEjecutivos(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public void atender(Cliente cliente, SolicitudAtencion solicitud) {

        switch (solicitud.getTipoInteraccion()) {

            case CONSULTA:
                consultar(cliente, solicitud.getIdProducto());
                break;

            case BAJA_PRODUCTO:
                bajaProducto(cliente, solicitud.getIdProducto());
                break;

            case MODIFICACION:
                modificarProducto(cliente, solicitud);
                break;

            default:
                break;
        }
    }

    private void consultar(Cliente cliente, String idProducto) {

        IProducto producto = buscarProducto(cliente, idProducto);

        if (producto == null) {
            return;
        }

        Interaccion interaccion = new Interaccion(generarId(), TipoInteraccion.CONSULTA);
        sucursal.registrarInteraccion(interaccion);
    }

    private void bajaProducto(Cliente cliente, String idProducto) {

        IProducto producto = buscarProducto(cliente, idProducto);

        if (producto == null) {
            return;
        }

        producto.modificarEstado(EstadoProducto.CANCELADO);
        
        Interaccion interaccion = new Interaccion(generarId(), TipoInteraccion.BAJA_PRODUCTO);
        sucursal.registrarInteraccion(interaccion);

        Documento documento = new Documento(generarId(), TipoDocumento.COMPROBANTE);
        sucursal.registrarDocumento(documento);
    }

    private void modificarProducto(Cliente cliente, SolicitudAtencion solicitud) {

        IProducto producto = buscarProducto(cliente, solicitud.getIdProducto());

        if (producto == null) {
            return;
        }

        Interaccion interaccion = new Interaccion(generarId(), TipoInteraccion.MODIFICACION);
        sucursal.registrarInteraccion(interaccion);
    }

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

    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}