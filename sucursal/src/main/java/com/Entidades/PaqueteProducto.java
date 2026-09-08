package com.Entidades;

import com.example.Enums.Moneda;

public class PaqueteProducto extends ProductoBase {

    public PaqueteProducto(String id, Moneda moneda) {
        super(id, moneda);
    }

    @Override
    public float getPrecioAPagar() {
        // Suma recursiva del precio de todos los productos del paquete
        final float[] total = {0.0f};
        recorrer(producto -> {
            // Evitamos sumar el precio del paquete mismo en la raíz para no duplicar
            if (producto != this) {
                total[0] += producto.getPrecioAPagar();
            }
        });
        return total[0];
    }
}

