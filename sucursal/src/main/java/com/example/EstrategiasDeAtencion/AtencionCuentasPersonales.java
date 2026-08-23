package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Sucursal;
import com.Entidades.Interaccion;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionCuentasPersonales implements IEstrategiaAtencion {

    private Sucursal sucursal;

    public AtencionCuentasPersonales(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    @Override
    public void atender(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {
        
        switch (solicitud.getTipoInteraccion()) {

            case CONSULTA:
                consultar(cliente, solicitud.getIdProducto());
                break;

            case PAGO:
                pagar(cliente, solicitud.getIdProducto(), solicitud.getMonto());
                break;

            case ALTA_PRODUCTO:
                altaCuenta(cliente, solicitud);
                break;

            default:
                break;
        }
    }

    private void consultar(Cliente cliente, String idProducto, String mostradorId) {
        
        Cuenta cuenta = buscarCuenta(cliente, idProducto);

        if (cuenta == null) {
            return;
        }
        double saldo = cuenta.getSaldo();
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    private void pagar(Cliente cliente, String idProducto, double monto, String mostradorId) {
        
        Cuenta cuenta = buscarCuenta(cliente, idProducto);
        
        if (cuenta == null) {
            return;
        }

        boolean pudoRetirar = cuenta.retirarEfectivo((int) monto);

        if (!pudoRetirar) {
            return;
        }

        Interaccion interaccion = new Interaccion(TipoInteraccion.PAGO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);

        Documento comprobante = new Documento(generarId(), TipoDocumento.COMPROBANTE_PAGO, cliente, java.time.LocalDate.now(), null);
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

    private void altaCuenta(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        Cuenta nuevaCuenta = new Cuenta(generarId(), solicitud.getMonto());
        cliente.agregarProducto(nuevaCuenta);
        registrarDocumentosPresentados(solicitud);
        Interaccion interaccion = new Interaccion(TipoInteraccion.ALTA_PRODUCTO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    private Cuenta buscarCuenta(Cliente cliente,String idProducto) {

        int indice = 0;
        while (indice < cliente.obtenerProductos().tamano()) {
            IProducto producto = cliente.obtenerProductos().obtener(indice);
            indice++;

            if (producto instanceof Cuenta cuenta && cuenta.getId().equals(idProducto)) {
                return cuenta;
            }
        }
        return null;
    }

    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}