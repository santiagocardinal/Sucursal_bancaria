package com.Entidades;

import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;

public class Sucursal {
    private String id; 
    private ListaArray<Sector> sectores;
    private HistorialInteracciones historialInteracciones;
    private CopiaDocumentos copiaDocumentos;
    private ListaEnlazada<Cliente> clientesTotales;

    public void agregarSector(Sector sector){
        sectores.agregar(sector);
    }
    
    public void registrarInteraccion(Interaccion interaccion){
        historialInteracciones.registrarInteraccion(interaccion);
    }

    public void registrarDocumento(Documento documento){
        copiaDocumentos.registrarDocumento(documento);
    }

    public Pila<Interaccion> ObtenerHistorialCliente(String ci){
        
        Cliente cliente = clientesTotales.buscar(clienteActual -> clienteActual.getCi().equals(ci));

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        historialInteracciones.obtenerPorCliente(cliente);
    } 

    public Pila<Interaccion> ObtenerDocumentosCliente(String ci){

        Cliente cliente = clientesTotales.buscar(clienteActual -> clienteActual.getCi().equals(ci));
        
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        historialInteracciones.obtenerPorCliente(cliente);
    }  

    public void registrarClienteEnSector(Cliente cliente, Sector sector){

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null");
        }

        if (sector == null) {
            throw new IllegalArgumentException("El sector no puede ser null");
        }

        if (!sectores.contiene(sector)) {
            throw new IllegalArgumentException("El sector no esta registrado en la sucursal");
        }

        clientesTotales.agregar(cliente);
        sector.agregarCliente(cliente);
    }

}
