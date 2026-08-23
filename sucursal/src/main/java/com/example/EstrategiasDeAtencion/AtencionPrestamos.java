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

    // Consulta sobre un préstamo puntual del cliente.
    private void consultar(Cliente cliente, String idProducto, String mostradorId) {

        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        // Si el cliente no tiene un préstamo con ese id, no hay nada que consultar se corta 
        if (prestamo == null) {
            return;
        }

        //double proximoMonto = prestamo.proximaCuota(); posible extensión futura, por si se quiere mostrar el monto de la próxima cuota al cliente

        // Se registra la interacción para la auditoría.
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    // Maneja el pago de una cuota de un préstamo.
    private void pagar(Cliente cliente, String idProducto, double monto, String mostradorId) {

        Prestamo prestamo = buscarPrestamo(cliente, idProducto);

        // Si no existe el préstamo, no hay nada que pagar.
        if (prestamo == null) {
            return;
        }

        // Se intenta registrar el pago. si no se concretó el pago, entonces se corta ahí sin registrar nada 
        try {
            prestamo.pagarCuota(monto);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return;
        }
        // Si no se lanzó ninguna excepción, el pago se concretó, se registran interacción y comprobante
        Interaccion interaccion = new Interaccion(TipoInteraccion.PAGO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);

        Documento comprobante = new Documento(generarId(),TipoDocumento.COMPROBANTE_PAGO, cliente, java.time.LocalDate.now(), null);
        sucursal.registrarDocumento(comprobante);
    }

    // Recorre los documentos que el cliente presentó junto con la solicitud y los registra uno por uno.
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

    // Da de alta un préstamo nuevo para el cliente.
    private void altaPrestamo(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        // Se crea el Prestamo con id generado, montoOriginal igual al
        // monto de la solicitud, interés del 0.10
        Prestamo prestamo = new Prestamo(generarId(), solicitud.getMonto(), 10, 12);
        // Se lo agrega a la lista de productos del cliente.
        cliente.agregarProducto(prestamo);
        // Se registran los documentos presentados para el trámite.
        registrarDocumentosPresentados(solicitud);
        // Se registra la interacción de auditoría del alta.
        Interaccion interaccion = new Interaccion(TipoInteraccion.ALTA_PRODUCTO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    // Busca, entre todos los productos del cliente, el que sea un
    // Prestamo Y tenga el id pedido 
    private Prestamo buscarPrestamo(Cliente cliente, String idProducto) {

        int indice = 0;
        while (indice < cliente.obtenerProductos().tamano()) {
            IProducto producto = cliente.obtenerProductos().obtener(indice);
            indice++;

         // Verifica si es un Prestamo y lo guarda automáticamente en "prestamo". Con && también comprueba que el ID coincida
            if (producto instanceof Prestamo prestamo && prestamo.getId().equals(idProducto)) {
                return prestamo;
            }
        }
        // No se encontró ningún Prestamo con ese id entre los productos del cliente 
        return null;
    }

    // Genera un id único (UUID) para préstamos y documentos nuevos.
    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}