package com.Entidades;

import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Enums.Moneda;

// PaqueteProducto es un contenedor que agrupa otros IProducto.
// Puede contener productos concretos u otros paquetes (patrón Composite).
public class PaqueteProducto extends ProductoBase {

    // Constructor: recibe el id del paquete y la moneda en la que opera,
    // y se los pasa tal cual al constructor de ProductoBase (que es quien
    // realmente los guarda y arma el estado inicial ACTIVO)
    public PaqueteProducto(String id, Moneda moneda) {
        super(id, moneda);
    }

    // getPrecioAPagar() su precio es la suma de los precios de todo lo que contiene, a cualquier nivel de anidamiento.
    @Override
    public float getPrecioAPagar() {

        float total = 0.0f;
        TDALista<IProducto> hijos = obtenerComponentes();

        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).getPrecioAPagar();
        }

        return total;
    }
}