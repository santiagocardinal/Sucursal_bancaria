package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.Cuenta;
import com.Entidades.IProducto;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionPrestamos implements IEstrategiaAtencion {

    private Sucursal sucursal;

    public AtencionPrestamos(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public void atender(Cliente cliente, SolicitudAtencion solicitud) {

        
        switch (solicitud.getTipoInteraccion()) {

            case CONSULTA:
                consultar(cliente, solicitud.getIdProducto());
                break;

            case PAGO:
                pagar(cliente, solicitud.getIdProducto(), solicitud.getMonto());
                break;

            case ALTA_PRODUCTO:
                altaPrestamo(cliente, solicitud.getMonto());
                break;

            default:
                break;
        }
    }

    private void consultar(Cliente cliente, String idProducto) {
        
        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        if (prestamo == null) {
            return;
        }

        prestamo.proximaCuota();

        Interaccion interaccion = new Interaccion(generarId(), TipoInteraccion.CONSULTA);
        sucursal.registrarInteraccion(interaccion);
    }

    private void pagar(Cliente cliente, String idProducto, double monto) {
        
        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        if (prestamo == null) {
            return;
        }
        
        prestamo.pagarCuota(monto);

        Interaccion interaccion = new Interaccion(generarId(),TipoInteraccion.PAGO);
        sucursal.registrarInteraccion(interaccion);

        Documento comprobante = new Documento(generarId(),TipoDocumento.COMPROBANTE);
        sucursal.registrarDocumento(comprobante);
    }

    private void altaPrestamo(Cliente cliente, double monto) {

        Prestamo prestamo = new Prestamo(monto,10,12,0);
        cliente.agregarProducto(prestamo);

        Interaccion interaccion = new Interaccion(generarId(), TipoInteraccion.ALTA_PRODUCTO);
        sucursal.registrarInteraccion(interaccion);
    }
     
    private Prestamo buscarPrestamo(Cliente cliente, String idProducto) {

        int indice = 0;
        while (indice < cliente.obtenerProductos().tamano()) {
            IProducto producto = cliente.obtenerProductos().obtener(indice);
            indice++;

            if (producto instanceof Prestamo prestamo && prestamo.getId().equals(idProducto)) {
                return prestamo;
            }
        }

        return null;
    }
    
    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}