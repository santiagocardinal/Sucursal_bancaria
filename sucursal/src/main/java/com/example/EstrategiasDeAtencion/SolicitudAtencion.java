package com.example.EstrategiasDeAtencion;

import com.Entidades.Documento;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.TipoInteraccion;

public class SolicitudAtencion {
    
    private ListaEnlazada<Documento> documentosPresentados;
    private TipoInteraccion tipoInteraccion;
    private String idProducto;
    private double monto;

    public SolicitudAtencion(TipoInteraccion tipoInteraccion, String idProducto, double monto, ListaEnlazada<Documento> documentosPresentados) {
        this.tipoInteraccion = tipoInteraccion;
        this.idProducto = idProducto;
        this.documentosPresentados = documentosPresentados;
      // El monto se usa en PAGO para abonar una cuota y en ALTA_PRODUCTO para indicar el monto inicial del nuevo producto.
        if (tipoInteraccion == TipoInteraccion.PAGO || tipoInteraccion == TipoInteraccion.ALTA_PRODUCTO) {
           // Es uno de los dos tipos donde el monto es relevante. se guarda el valor que llegó por parámetro tal cual.
            this.monto = monto;        
        }else{
            // Cualquier otro tipo de interacción, se ignora lo que hayan pasado en "monto" y se fuerza a 0.
            this.monto = 0;
        }
    }

    public TipoInteraccion getTipoInteraccion() {
        if(tipoInteraccion == null)
        {
            throw new IllegalArgumentException("El tipo de interacción no puede ser null");
        }
        return tipoInteraccion;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public double getMonto() {
        return monto;
    }

    public ListaEnlazada<Documento> getDocumentosPresentados() {
        return documentosPresentados;
    }
}