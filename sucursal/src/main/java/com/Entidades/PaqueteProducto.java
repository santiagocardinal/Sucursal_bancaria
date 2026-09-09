package com.Entidades;

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

        // Array de tamaño 1 usado como acumulador mutable
        final float[] total = {0.0f};

        // recorrer() visita este paquete y TODOS sus componentes descendientes sin importar la profundidad), aplicando la acción a cadauno de estos
       
        recorrer(producto -> {

            if (producto != this) {
                // Para cada componente (sea hoja o paquete), le pedimos su propio precio y lo sumamos.
                total[0] += producto.getPrecioAPagar();
            }
        });

        // Devolvemos el acumulado la suma de precios de todos los paquetes
        return total[0];
    }
}