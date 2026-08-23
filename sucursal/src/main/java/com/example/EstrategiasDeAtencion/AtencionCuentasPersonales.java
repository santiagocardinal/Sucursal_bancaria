package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.Cuenta;
import com.Entidades.Documento;
import com.Entidades.IProducto;
import com.Entidades.Sucursal;
import com.Entidades.Interaccion;
import com.example.Enums.TipoDocumento;
import com.example.Enums.TipoInteraccion;

public class AtencionCuentasPersonales implements IEstrategiaAtencion {

    private Sucursal sucursal;

    public AtencionCuentasPersonales(Sucursal sucursal) {
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
                altaCuenta(cliente, solicitud.getMonto());
                break;

            default:
                break;
        }
    }

    private void consultar(Cliente cliente, String idProducto) {
        
        Cuenta cuenta = buscarCuenta(cliente, idProducto);

        if (cuenta == null) {
            return;
        }
        double saldo = cuenta.getSaldo();
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi());
        sucursal.registrarInteraccion(interaccion);
    }

    private void pagar(Cliente cliente, String idProducto, double monto) {
        
        Cuenta cuenta = buscarCuenta(cliente, idProducto);
        
        if (cuenta == null) {
            return;
        }

        boolean pudoRetirar = cuenta.retirarEfectivo((int) monto);

        if (!pudoRetirar) {
            return;
        }

        Interaccion interaccion = new Interaccion(TipoInteraccion.PAGO, cliente.getCi());
        sucursal.registrarInteraccion(interaccion);

        Documento comprobante = new Documento(generarId(), TipoDocumento.COMPROBANTE_PAGO, cliente, java.time.LocalDate.now(), null);
        sucursal.registrarDocumento(comprobante);
    }

    private void altaCuenta(Cliente cliente, double saldoInicial) {

        Cuenta nuevaCuenta = new Cuenta(generarId(), saldoInicial);
        cliente.agregarProducto(nuevaCuenta);

        Interaccion interaccion = new Interaccion(TipoInteraccion.ALTA_PRODUCTO, cliente.getCi());
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