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
                consultar(cliente, solicitud.getIdProducto(), mostradorId);
                break;

            case PAGO:
                pagar(cliente, solicitud.getIdProducto(), solicitud.getMonto(), mostradorId);
                break;

            case ALTA_PRODUCTO:
                altaCuenta(cliente, solicitud, mostradorId);
                break;

            default:
                break;
        }
    }

    // Maneja una consulta sobre una cuenta puntual del cliente.
    private void consultar(Cliente cliente, String idProducto, String mostradorId) {

        Cuenta cuenta = buscarCuenta(cliente, idProducto);

        // Si el cliente no tiene una cuenta con ese id, no hay nada que consultar, se corta acá sin registrar ninguna interacción
        if (cuenta == null) {
            return;
        }

       
        //double saldo = cuenta.getSaldo(); posible extensión futura, por si se quiere mostrar el saldo que tiene esa cuenta bancaria

        // Se registra la interacción para la auditoría, qué cliente, qué tipo de trámite, y en qué mostrador.
        Interaccion interaccion = new Interaccion(TipoInteraccion.CONSULTA, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }

    // Maneja un pago  sobre una cuenta en especifico
    private void pagar(Cliente cliente, String idProducto, double monto, String mostradorId) {

        Cuenta cuenta = buscarCuenta(cliente, idProducto);

        // Si no existe la cuenta, no hay de dónde retirar, se corta acá.
        if (cuenta == null) {
            return;
        }

        // Vamos a trabajar con enteros para facilitar el tema de los tests y corroborar un buen funcionamiento (para la siguiente entrega se va a arreglar esto)
        boolean pudoRetirar = cuenta.retirarEfectivo((int) monto);

        // Si no se pudo retirar (saldo insuficiente),no se registra nada, el pago simplemente no se concretó.
        if (!pudoRetirar) {
            return;
        }

        // Si el retiro fue exitoso, se registran estas cosas:
        // 1) la interacción, para la auditoría.
        Interaccion interaccion = new Interaccion(TipoInteraccion.PAGO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);

        // 2) un comprobante de pago, como Documento nuevo, con la fecha
        // de hoy como fecha de presentación y sin vigencia (no vence)
        Documento comprobante = new Documento(generarId(), TipoDocumento.COMPROBANTE_PAGO, cliente, java.time.LocalDate.now(), null);
        sucursal.registrarDocumento(comprobante);
    }

    // Recorre la lista de documentos que el cliente trajo junto con la
    // solicitud (por ejemplo, cédula y comprobante de ingresos para abrir
    // una cuenta nueva) y los va registrando uno por uno en la sucursal.
    private void registrarDocumentosPresentados(SolicitudAtencion solicitud) {
        ListaEnlazada<Documento> documentos = solicitud.getDocumentosPresentados();
        // Si la solicitud no trae documentos (lista nula), no hay nada
        // que registrar.
        if (documentos == null) {
            return;
        }
       
        int indice = 0;
        while (indice < documentos.tamano()) {
            sucursal.registrarDocumento(documentos.obtener(indice));
            indice++;
        }
    }

    // Maneja la creacion de una cuenta nueva de un cliente
    private void altaCuenta(Cliente cliente, SolicitudAtencion solicitud, String mostradorId) {

        // Se crea la Cuenta con un id generado automáticamente (no lo elige el cliente ni el empleado) y con saldo inicial igual al monto que venía en la solicitud 
        Cuenta nuevaCuenta = new Cuenta(generarId(), solicitud.getMonto());
        // Se la agrega a la lista de productos del cliente.
        cliente.agregarProducto(nuevaCuenta);
        // Se registran los documentos que haya presentado para este trámite (cédula, etc.).
        registrarDocumentosPresentados(solicitud);
        // Se registra la interacción de auditoría correspondiente al alta.
        Interaccion interaccion = new Interaccion(TipoInteraccion.ALTA_PRODUCTO, cliente.getCi(), mostradorId);
        sucursal.registrarInteraccion(interaccion);
    }


    private Cuenta buscarCuenta(Cliente cliente,String idProducto) {

        int indice = 0;
        while (indice < cliente.obtenerProductos().tamano()) {
            IProducto producto = cliente.obtenerProductos().obtener(indice);
            indice++;

         // "instanceof Cuenta cuenta" verifica si es una Cuenta y la guarda en "cuenta".
        // Con && también se comprueba que el ID coincida. Si no es una Cuenta, da false.
            if (producto instanceof Cuenta cuenta && cuenta.getId().equals(idProducto)) {
                return cuenta;
            }
        }
        // Se recorrió toda la lista y no se encontró ninguna Cuenta con
        // ese id retorna null
        return null;
    }

    // Genera un identificador único para productos y documentos nuevos,
    // usando UUID (una librería estándar de Java para generar ids)
    private String generarId() {
        return java.util.UUID.randomUUID().toString();
    }
}