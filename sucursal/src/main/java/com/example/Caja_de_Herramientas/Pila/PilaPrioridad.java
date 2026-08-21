package com.example.Caja_de_Herramientas.Pila;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import com.example.Caja_de_Herramientas.Cola.NodoPrioridad;
import com.example.Caja_de_Herramientas.Lista.Nodo;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class PilaPrioridad<T> implements TDAPila<T> 
{
    private NodoPrioridad<T> tope;
    private int tamano;

    public PilaPrioridad() 
    {
        tope = null;
        tamano = 0;
    }

    //los 3 metodos siguientes son metodos propias de las pilas
    @Override
    public void mete(T dato) 
    {
        mete(dato, 0); //prioridad default 0
    }

    public void mete(T dato, int prioridad) 
    {
        if (dato == null)
        {
            throw new IllegalArgumentException("No se permiten null");
        }

        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato, prioridad);

        if (tope == null || prioridad >= tope.getPrioridad())
        {
            nuevo.setSiguiente(tope);
            tope = nuevo;
            tamano++;
            return;
        }

        NodoPrioridad<T> actual = tope;

        while (actual.getSiguiente() != null && ((NodoPrioridad<T>) actual.getSiguiente()).getPrioridad() > prioridad)
        {
            actual = (NodoPrioridad<T>) actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        tamano++;
    }

    @Override
    public T saca() 
    {
        if (esVacio())
        {
            throw new NoSuchElementException("Pila vacía");
        }

        NodoPrioridad<T> eliminado = tope;
        tope = (NodoPrioridad<T>) tope.getSiguiente();
        eliminado.setSiguiente(null);
        tamano--;

        return eliminado.getDato();
    }

    @Override
    public T tope() 
    {
        if (esVacio())
        {
            throw new NoSuchElementException("Pila vacía");
        }

        return tope.getDato();
    }

    public int prioridadTope()
    {
        if (esVacio())
        {
            throw new NoSuchElementException("Pila vacía");
        }

        return tope.getPrioridad();
    }

    @Override
    public boolean esVacio()
    {
        return tope == null;
    }

    @Override
    public int tamano() 
    {
        return tamano;
    }

    @Override
    public void vaciar()
    {
        tope = null;
        tamano = 0;
    }

    //estos metodos son los metodos que se heredaron de TDALista
    @Override
    public boolean contiene(T elem)
    {
        if (elem == null) return false;

        Nodo<T> actual = tope;

        while (actual != null)
        {
            if (actual.getDato().equals(elem))
            {
                return true;
            }

            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public int indiceDe(T elem)
    {
        if (elem == null) return -1;

        Nodo<T> actual = tope;
        int indice = 0;

        while (actual != null)
        {
            if (actual.getDato().equals(elem))
            {
                return indice;
            }

            actual = actual.getSiguiente();
            indice++;
        }

        return -1;
    }

    @Override
    public T obtener(int index)
    {
        if (index < 0 || index >= tamano)
        {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        Nodo<T> actual = tope;
        int i = 0;

        while (actual != null)
        {
            if (i == index)
            {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
            i++;
        }

        throw new IndexOutOfBoundsException("Índice: " + index);
    }

    @Override
    public T buscar(Predicate<T> criterio)
    {
        if (criterio == null)
        {
            throw new IllegalArgumentException(
                "El criterio no puede ser null"
            );
        }

        Nodo<T> actual = tope;

        while (actual != null)
        {
            if (criterio.test(actual.getDato()))
            {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    @Override
    public void agregar(T elem)
    {
        mete(elem);
    }

    @Override
    public void agregar(int index, T elem)
    {
        throw new UnsupportedOperationException("No aplica en pila con prioridad");
    }

    @Override
    public T remover(int index)
    {
        if (index < 0 || index >= tamano)
        {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }
        if (index == 0)
        {
            return saca();
        }

        NodoPrioridad<T> anterior = tope;
        int i = 0;

        while (i < index - 1)
        {
            anterior = (NodoPrioridad<T>) anterior.getSiguiente();
            i++;
        }

        NodoPrioridad<T> eliminado = (NodoPrioridad<T>) anterior.getSiguiente();

        anterior.setSiguiente(eliminado.getSiguiente());

        eliminado.setSiguiente(null);

        tamano--;

        return eliminado.getDato();
    }

    @Override
    public boolean remover(T elem)
    {
        if (elem == null || tope == null)
        {
            return false;
        }

        NodoPrioridad<T> actual = tope;
        NodoPrioridad<T> anterior = null;

        while (actual != null)
        {
            if (actual.getDato().equals(elem))
            {
                if (anterior == null)
                {
                    tope = (NodoPrioridad<T>) actual.getSiguiente();
                }
                else
                {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                actual.setSiguiente(null);

                tamano--;

                return true;
            }

            anterior = actual;
            actual = (NodoPrioridad<T>) actual.getSiguiente();
        }

        return false;
    }

    //==========================AUNQUE SEA UNA PILA CON PRIORIDAD NO SE PUEDEN HACER LAS SIGUIENTES COSAS======================
    @Override
    public TDALista<T> ordenar(Comparator<T> comparator)
    {
        throw new UnsupportedOperationException("No aplica en pila con prioridad");
    }
}