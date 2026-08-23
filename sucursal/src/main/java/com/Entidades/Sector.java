package com.Entidades;

import com.example.Caja_de_Herramientas.Cola.ColaPrioridad;
import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Enums.NivelPrioridad;
import com.example.EstrategiasDeAtencion.SolicitudAtencion;

// Representa un sector de atención dentro de una sucursal (por ejemplo,
// "Cuentas Personales" o "Préstamos"). Un sector tiene mostradores que
// atienden clientes, y una cola de espera donde los clientes aguardan
// su turno según su nivel de prioridad.
public class Sector
{
    private ListaArray<Mostrador> mostradores;

    private ColaPrioridad<Cliente> colaEspera;

    private Sucursal sucursal;

    // Contador  que lleva la cuenta de qué número de turno le toca
    // al próximo cliente que entre a este sector. Es independiente de la
    // prioridadm, el turno es solo un número de orden de llegada, no indica
    // quién se atiende primero.
    private int contadorTurnos;

    public Sector(Sucursal sucursal)
    {
        // Un sector no puede existir sin pertenecer a una sucursal por lo que se corta acá si llega null, antes de inicializar nada.
        if (sucursal == null)
        {
            throw new IllegalArgumentException("la sucursal no puede ser nula");
        }
        this.sucursal = sucursal;
  
        this.mostradores = new ListaArray<>();
        this.colaEspera = new ColaPrioridad<>();
        // Arranca en 1 porque el primer número que tenga un cliente es más
        // coherente que sea el 1 y no el 0.
        this.contadorTurnos = 1;
    }

   public int getContadorTurnos()
    {
        return contadorTurnos;
    }

    // Método privado que le asigna al cliente el número de turno actual y después incrementa el contador para el próximo cliente.
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
        // Un mostrador se crea ya "apuntando" a un sector específico
        // (ver Mostrador). Acá se chequea que ese sector sea justamente
        // ESTE sector ("this"): evita que, por error, se agregue a un
        // sector un mostrador que en realidad pertenece a otro.
        if (mostrador.getSector() != this)
        {
            throw new IllegalArgumentException("el mostrador pertenece a otro sector");
        }

        // Solo se agrega si todavía no estaba en la lista
        if (!mostradores.contiene(mostrador))
        {
            mostradores.agregar(mostrador);
        }
    }

    // Método privado: recorre los mostradores del sector y devuelve el
    // primero que esté libre (o null si no hay ninguno libre). 
    private Mostrador buscarMostradorLibre()
    {
        return mostradores.buscar(m -> m.estaLibre());
    }

    // Hace que un cliente entre a la sala de espera de este sector.
    public void recibirCliente(Cliente cliente, NivelPrioridad prioridad, SolicitudAtencion solicitud)
    {
        
        if (cliente == null)
        {
            throw new IllegalArgumentException("el cliente no puede ser nulo");
        }
        if (prioridad == null)
        {
            throw new IllegalArgumentException("la prioridad no puede ser nula");
        }
        if (solicitud == null)
        {
        throw new IllegalArgumentException("la solicitud no puede ser nula");
        }
      
        cliente.setSolicitudActual(solicitud);
        // Se le asigna su número de turno 
        asignarTurno(cliente);

        colaEspera.poneEnCola(cliente, prioridad);
    }


    public boolean llamarClienteAMostrador()
    {
        if(colaEspera.esVacio())
        {
            return false;
        }
        Mostrador mostradorLibre = buscarMostradorLibre();//Si no hay nadie esprando se pone ese moestrador en el estao de LIBRE

        if (mostradorLibre == null)
        {
            return false;//retorna false porque no se puede usar, es inutil usar un mostrador que sea nulo
        }
        // Si se llegó hasta acá, hay cliente Y hay mostrador libre. se saca al cliente del frente de la cola (el de mayor prioridad)
        Cliente cliente = colaEspera.quitaDeCola();
        // ...y se le pide a ESE mostrador que lo atienda.
        mostradorLibre.atender(cliente);
        return true; //si llegamos hasta aca, asumimos que se atendió 
    }

    //consulta pedida en letra

    // Devuelve en qué posición de la cola de espera está un cliente Le delega el trabajo a
    // ColaPrioridad.posicionDe(), que es quien realmente sabe cómo está ordenada la cola internamente.
    public int estimarPosicionEnCola(Cliente cliente)
    {
        if (cliente == null)
        {
            throw new IllegalArgumentException("el cliente no puede ser nulo");
        }
        return colaEspera.posicionDe(cliente);
    }

    //permite dimensionar la demanda de cada sector sin exponer la
    //implementación interna de la cola de prioridad. Es O(1) porque ColaPrioridad
    //mantiene su propio contador de tamaño (no recorre la cola para calcularlo).
    public int cantidadEnEspera()
    {
        return colaEspera.tamano();
    }
}