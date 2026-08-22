package com.Entidades;

import com.example.Caja_de_Herramientas.Cola.ColaPrioridad;
import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Enums.NivelPrioridad;

public class Sector 
{
    private ListaArray<Mostrador> mostradores;
    private ColaPrioridad<Cliente> colaEspera;
    private Sucursal sucursal;
    private int contadorTurnos;

    public Sector(Sucursal sucursal)
    {
        if (sucursal == null)
        {
            throw new IllegalArgumentException("la sucursal no puede ser nula");
        }

        this.sucursal = sucursal;
        this.mostradores = new ListaArray<>();
        this.colaEspera = new ColaPrioridad<>();
        this.contadorTurnos = 1;   //arranca en 1 porque el primer numero que tenga un cliente es más coherente que sea el 1 y no el 0
    }

    public int getContadorTurnos()
    {
        return contadorTurnos;
    }

    private void asignarTurno(Cliente cliente) 
    {
        if (cliente == null) 
        {
            throw new IllegalArgumentException("el cliente no puede ser nulo");
        }
        cliente.setNumeroTurno(contadorTurnos);
        contadorTurnos++;
    }

    public void agregarMostrador(Mostrador mostrador)
    {
        if (mostrador == null) 
        {
            throw new IllegalArgumentException("el mostrador no puede ser nulo");
        }
        if (mostrador.getSector() != this)
        {
            throw new IllegalArgumentException("el mostrador pertenece a otro sector");
        }

        if (!mostradores.contiene(mostrador))
        {
            mostradores.agregar(mostrador);
        }
    }

    public Mostrador buscarMostradorLibre()
    {
        return mostradores.buscar(m -> m.estaLibre());
    }

    public void recibirCliente(Cliente cliente, NivelPrioridad prioridad)
    {
        if (cliente == null)
        {
            throw new IllegalArgumentException("el cliente no puede ser nulo");
        }
        if (prioridad == null)
        {
            throw new IllegalArgumentException("la prioridad no puede ser nula");
        }

        asignarTurno(cliente);
        colaEspera.poneEnCola(cliente, prioridad);
    }

    public boolean llamarClienteAMostrador()
    {
        if(colaEspera.esVacio())
        {
            return false;
        }
        Mostrador mostradorLibre = buscarMostradorLibre();

        if (mostradorLibre == null)
        {
            return false;
        }
        Cliente cliente = colaEspera.quitaDeCola(); 
        return mostradorLibre.atender(cliente);
    }  
}
