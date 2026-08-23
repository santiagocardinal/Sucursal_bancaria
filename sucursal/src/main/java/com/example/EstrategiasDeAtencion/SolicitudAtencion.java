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
        
        if (tipoInteraccion == TipoInteraccion.PAGO || tipoInteraccion == TipoInteraccion.ALTA_PRODUCTO) {
            this.monto = monto;        
        }else{
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