package com.Entidades;

import java.util.Objects;

import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.NivelPrioridad;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

public class Sucursal {
    private String id; 
    private ListaArray<Sector> sectores;
    private HistorialInteracciones historialInteracciones;
    private CopiaDocumentos copiaDocumentos;
    private ListaEnlazada<Cliente> clientesTotales;

    public Sucursal(String id) {
    this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
    this.sectores = new ListaArray<>();
    this.clientesTotales = new ListaEnlazada<>();
    this.copiaDocumentos = new CopiaDocumentos();
    this.historialInteracciones = new HistorialInteracciones();
}

    public void agregarSector(Sector sector){
        if (sector == null) {
            throw new IllegalArgumentException("El sector no puede ser null");
        }
        sectores.agregar(sector);
    }
    
    public void registrarInteraccion(Interaccion interaccion){
        historialInteracciones.registrarInteraccion(interaccion);
    }

    public void registrarDocumento(Documento documento){
        copiaDocumentos.registrarDocumento(documento);
    }

    //consulta pedida en la letra
    public Pila<Interaccion> obtenerHistorialCliente(String ci){
        
        Cliente cliente = clientesTotales.buscar(clienteActual -> clienteActual.getCi().equals(ci));

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }

        return historialInteracciones.obtenerPorCliente(cliente.getCi());
    } 

    //consulta pedida en la letra
    public Pila<Documento> obtenerDocumentosCliente(String ci){

        Cliente cliente = clientesTotales.buscar(clienteActual -> clienteActual.getCi().equals(ci));
        
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no existe");
        }
        return copiaDocumentos.obtenerPorCliente(cliente);
    }  

    public void registrarClienteEnSector(Cliente cliente, Sector sector, NivelPrioridad prioridad, SolicitudAtencion solicitud){

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser null");
        }

        if (sector == null) {
            throw new IllegalArgumentException("El sector no puede ser null");
        }

        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser null");
        }
        if (solicitud == null) {
        throw new IllegalArgumentException("La solicitud no puede ser null");
        }

        if (!sectores.contiene(sector)) {
            throw new IllegalArgumentException("El sector no esta registrado en la sucursal");
        }

        clientesTotales.agregar(cliente);
        sector.recibirCliente(cliente, prioridad, solicitud);
    }

}
