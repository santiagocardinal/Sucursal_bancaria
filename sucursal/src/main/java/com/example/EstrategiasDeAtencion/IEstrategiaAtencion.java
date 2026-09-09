package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;
import com.Entidades.IProducto;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Enums.Moneda;


public interface IEstrategiaAtencion {

    void atender(Cliente cliente, SolicitudAtencion solicitudAtencion, String mostradorId);
    
    default ListaEnlazada<Float> posicionConsolidada(IProducto producto) {
        class Totales {
            float usd = 0;
            float pesoUruguayo = 0;
        }

        Totales totales = new Totales();
        producto.recorrer(subProd -> {
            // recorrer() visita todo el árbol (raíz, paquetes intermedios y
            // hojas) de una sola pasada.
            if (!subProd.esHoja()) {
                return;
            }

            if (subProd.getMoneda() == Moneda.USD) {
                totales.usd += subProd.getPrecioAPagar();
            } else if (subProd.getMoneda() == Moneda.PESO_URUGUAYO) {
                totales.pesoUruguayo += subProd.getPrecioAPagar();
            }
        });

        ListaEnlazada<Float> resultado = new ListaEnlazada<>();
        resultado.agregar(totales.usd);
        resultado.agregar(totales.pesoUruguayo);
        return resultado;
    }

    default String imprimirPosicionConsolidada(IProducto producto) {
        ListaEnlazada<Float> totales = posicionConsolidada(producto);
        return "USD: " + totales.obtener(0) + " | PESO_URUGUAYO: " + totales.obtener(1);
    }
}