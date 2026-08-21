package com.example.Caja_de_Herramientas.Cola;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Enums.NivelPrioridad;
import com.example.Caja_de_Herramientas.Lista.Nodo;

public class ColaPrioridad<T> implements TDAColaPrioridad<T> 
{
    private NodoPrioridad<T> frente;
    private int tamano;

    public ColaPrioridad() 
    {
        this.frente = null;
        this.tamano = 0;
    }

    @Override
    public boolean poneEnCola(T dato, NivelPrioridad prioridad)
    {
        if (dato == null) return false;

        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato, prioridad.getValor());

        // Caso 1: cola vacía, o el nuevo tiene MÁS prioridad que el frente
        // (menor getValor() = más urgente, según cómo armaste el enum)
        if (frente == null || nuevo.getPrioridad() < frente.getPrioridad()) 
        {
            nuevo.setSiguiente(frente);
            frente = nuevo;
            tamano++;
            return true;
        }

        // Caso 2: recorrer buscando el lugar correcto.
        NodoPrioridad<T> actual = frente;
        while (actual.getSiguiente() != null &&
               ((NodoPrioridad<T>) actual.getSiguiente()).getPrioridad() <= nuevo.getPrioridad()) 
        {
            actual = (NodoPrioridad<T>) actual.getSiguiente();
        }

        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        tamano++;

        return true;
    }

    @Override
    public T frente() 
    {
        if (vacia()) throw new NoSuchElementException("Cola vacía");
        return frente.getDato();
    }

    @Override
    public T quitaDeCola() 
    {
        if (vacia()) throw new NoSuchElementException("Cola vacía");

        T dato = frente.getDato();
        NodoPrioridad<T> eliminado = frente;
        frente = (NodoPrioridad<T>) frente.getSiguiente();
        eliminado.setSiguiente(null);
        tamano--;

        return dato;
    }

    @Override
    public boolean poneEnCola(T dato) 
    {
        return poneEnCola(dato, NivelPrioridad.NORMAL);
    }

    public boolean vacia() 
    {
        return frente == null;
    }

    @Override
    public void anula() 
    {
        frente = null;
        tamano = 0;
    }

    @Override
    public int tamano() 
    {
        return tamano;
    }

    @Override
    public boolean esVacio() 
    {
        return vacia();
    }

    @Override
    public void vaciar() 
    {
        anula();
    }

    @Override
    public boolean contiene(T elem) 
    {
        if (elem == null) return false;

        Nodo<T> actual = frente;
        while (actual != null) 
        {
            if (actual.getDato().equals(elem)) return true;
            actual = actual.getSiguiente();
        }
        return false;
    }

    @Override
    public int indiceDe(T elem) 
    {
        if (elem == null) return -1;

        Nodo<T> actual = frente;
        int indice = 0;
        while (actual != null) 
        {
            if (actual.getDato().equals(elem)) return indice;
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

        Nodo<T> actual = frente;
        int i = 0;
        while (actual != null) 
        {
            if (i == index) return actual.getDato();
            actual = actual.getSiguiente();
            i++;
        }

        throw new IndexOutOfBoundsException("Índice: " + index);
    }

    @Override
    public T buscar(Predicate<T> criterio) 
    {
        if (criterio == null) return null;

        Nodo<T> actual = frente;
        while (actual != null) 
        {
            if (criterio.test(actual.getDato())) return actual.getDato();
            actual = actual.getSiguiente();
        }
        return null;
    }

    public T quitar(T elemento) 
    {
        if (frente == null || elemento == null) return null;

        Nodo<T> actual = frente;
        Nodo<T> anterior = null;

        while (actual != null) 
        {
            if (actual.getDato().equals(elemento)) 
            {
                if (anterior == null)
                {
                    frente = (NodoPrioridad<T>) actual.getSiguiente();
                }
                else
                {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                actual.setSiguiente(null);
                tamano--;
                return actual.getDato();
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return null;
    }

    public boolean eliminar(T elemento) 
    {
        return quitar(elemento) != null;
    }

    public T quitar(int indice) 
    {
        if (indice < 0 || indice >= tamano)
        {
            throw new IndexOutOfBoundsException("Índice: " + indice);
        }

        if (indice == 0)
        {
            return quitaDeCola();
        }

        Nodo<T> anterior = frente;
        int i = 0;
        while (i < indice - 1)
        {
            anterior = anterior.getSiguiente();
            i++;
        }

        Nodo<T> eliminado = anterior.getSiguiente();
        anterior.setSiguiente(eliminado.getSiguiente());
        eliminado.setSiguiente(null);
        tamano--;

        return eliminado.getDato();
    }

    @Override
    public T remover(int index) 
    {
        return quitar(index);
    }

    @Override
    public boolean remover(T elem) 
    {
        return eliminar(elem);
    }

    // Agrega con prioridad NORMAL por defecto
    @Override
    public void agregar(T elem) 
    {
        if (!poneEnCola(elem, NivelPrioridad.NORMAL))
        {
            throw new IllegalArgumentException("No se permiten null");
        }
    }

    @Override
    public void agregar(int index, T elem) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }
}