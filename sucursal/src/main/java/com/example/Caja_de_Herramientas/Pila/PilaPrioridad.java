package com.example.Caja_de_Herramientas.Pila;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.Nodo;

public class PilaPrioridad<T> extends ListaEnlazada<T> implements TDAPila<T> 
{
    private Nodo<T> tope;
    private int cantidad;

    public PilaPrioridad() 
    {
        tope = null;
        cantidad = 0;
    }

    @Override
    public void mete(T dato) 
    {
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(tope); 
        tope = nuevo;             
        cantidad++;
    }

    @Override
    public T saca() 
    {
        if (esVacia()) throw new RuntimeException("Pila vacía");
        T dato = tope.getDato();
        tope = tope.getSiguiente(); 
        cantidad--;
        return dato;
    }

    @Override
    public T tope() 
    {
        if (esVacia()) throw new RuntimeException("Pila vacía");
        {
            return tope.getDato();
        }
    }

    //@Override
    public boolean esVacia() 
    {
        return tope == null;
    }

    @Override
    public int tamano() 
    {
        return cantidad;
    }
}