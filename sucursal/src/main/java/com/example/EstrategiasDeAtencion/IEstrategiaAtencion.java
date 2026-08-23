package com.example.EstrategiasDeAtencion;

import com.Entidades.Cliente;

public interface IEstrategiaAtencion {
    
    void atender(Cliente cliente, SolicitudAtencion solicitudAtencion, String mostradorId);
}
