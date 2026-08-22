package com.example.EstrategiasDeAtencion;

import com.example.Enums.TipoInteraccion;

public class SolicitudAtencion {

    private TipoInteraccion tipoInteraccion;
    private String idProducto;
    private double monto;

    public SolicitudAtencion(TipoInteraccion tipoInteraccion, String idProducto, double monto) {
        this.tipoInteraccion = tipoInteraccion;
        this.idProducto = idProducto;
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
}