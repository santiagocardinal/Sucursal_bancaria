package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.IProducto;
import com.Entidades.Sucursal;
import com.Entidades.Interaccion;
import com.Entidades.Documento;
import com.Entidades.Prestamo;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionPrestamos implements IEstrategiaAtencion {

    private Sucursal sucursal;

    public AtencionPrestamos(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public void atender(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        
        switch (solicitud.getTipoInteraccion()) {

            case CONSULTA:
                consultar(cliente, solicitud.getIdProducto(), mostradorId);
                break;

            case PAGO:
                pagar(cliente, solicitud.getIdProducto(), solicitud.getMonto(), mostradorId);
                break;

            case ALTA_PRODUCTO:
                altaPrestamo(cliente, solicitud, mostradorId);
                break;

            default:
                break;
        }
    }

    private void consultar(Cliente cliente, String idProducto, String mostradorId) {
        
        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        if (prestamo == null) {
            return;
        }

        //double proximoMonto = prestamo.proximaCuota(); posible extensión futura, por si se quiere mostrar el monto de la próxima cuota al cliente

        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    private void pagar(Cliente cliente, String idProducto, double monto, String mostradorId) {
        
        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        if (prestamo == null) {
            return;
        }
        
        try {
            prestamo.pagarCuota(monto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return;
        }
        Interaccion interaccion = new Interaccion(TipoInteraccion.PAGO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);

        Documento comprobante = new Documento(generarId(),TipoDocumento.COMPROBANTE_PAGO, cliente, java.time.LocalDate.now(), null);
        sucursal.registrarDocumento(comprobante);
    }

    private void registrarDocumentosPresentados(SolicitudAtencion solicitud) {
        ListaEnlazada<Documento> documentos = solicitud.getDocumentosPresentados();
        if (documentos == null) {
            return;
        }
        int indice = 0;
        while (indice < documentos.tamano()) {
            sucursal.registrarDocumento(documentos.obtener(indice));
            indice++;
        }
    }

    private void altaPrestamo(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        Prestamo prestamo = new Prestamo(generarId(), solicitud.getMonto(), 10, 12);
        cliente.agregarProducto(prestamo);
        registrarDocumentosPresentados(solicitud);
        Interaccion interaccion = new Interaccion(TipoInteraccion.ALTA_PRODUCTO, cliente.getCi(), mostradorId);
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