package com.Entidades;

import com.example.EstrategiasDeAtencion.IEstrategiaAtencion;

public class Mostrador 
{
    private String id;
    private Sector sector;
    private boolean libre;
    private IEstrategiaAtencion estrategiaAtencion;

    public Mostrador(String id, Sector sector, IEstrategiaAtencion estrategiaAtencion, boolean libre)
    {
        this.id = id;
        this.sector = sector;
        this.libre = libre;
        this.estrategiaAtencion = estrategiaAtencion;
    }

    public boolean estaLibre()
    {
        return libre;
    }

    public void liberar()
    {
        this.libre = true;
    }

    public void atender(Cliente cliente)
    {
        if (cliente != null && estaLibre()) 
        {
            this.libre = false;                 //el mostrador pasa a estar ocupado ahora
            if (estrategiaAtencion != null) 
            {
                estrategiaAtencion.atender(cliente, cliente.getSolicitudActual(), this.id);    //se elige una estrategia para saber como atender al cliente
            }
        }
    } 

    public String getId()
    {
        return id;
    }

    public Sector getSector()
    {
        return sector;
    }

    public IEstrategiaAtencion getEstrategiaAtencion()
    {
        return estrategiaAtencion;
    }

    public void setEstrategiaAtencion(IEstrategiaAtencion estrategiaAtencion)
    {
        this.estrategiaAtencion = estrategiaAtencion;
    }
    

}
