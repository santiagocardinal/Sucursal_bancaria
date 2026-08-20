package com.example.Caja_de_Herramientas.Pila;

import java.util.NoSuchElementException;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;

public class Pila<T> extends ListaEnlazada<T> implements TDAPila<T> 
{
    //los 3 metodos siguientes son metodos propias de las pilas
    @Override
    public void mete(T dato) 
    {
        agregar(0, dato);  
    }

    @Override
    public T saca() 
    {
        if (esVacio()) 
        {
            throw new NoSuchElementException("la pila esta vacia");
        }

        return remover(0);
    }

    @Override
    public T tope() 
    {
        if (esVacio()) 
        {
            throw new NoSuchElementException("la pila esta vacia");
        }

        return obtener(0);
    }
}