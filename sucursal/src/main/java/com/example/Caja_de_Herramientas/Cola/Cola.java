package com.example.Caja_de_Herramientas.Cola;

import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.function.Predicate;
import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.Nodo;
import com.example.Caja_de_Herramientas.Lista.TDALista;

public class Cola<T> extends ListaEnlazada<T> implements TDACola<T> 
{
    private NodoPrioridad<T> frente;
    private int tamano;

    public Cola() 
    {
        this.frente = null;
        this.tamano = 0;
    }

    //los 4 metodos siguientes son metodos propias de las colas
    @Override
    public T frente()
    {
        if (esVacio()) 
        {
            throw new NoSuchElementException("la cola esta vacia");
        }
        return obtener(0);
    }

    @Override
    public boolean poneEnCola(T dato)
    {
        agregar(dato);
        return true;
    }

    @Override
    public T quitaDeCola()
    {
        if (esVacio()) 
        {
            throw new NoSuchElementException("la pila esta vacia");
        }
        return remover(0);

    }

    @Override
    public void anula() 
    {
        frente = null;
        tamano = 0;
    }

    //estos metodos son los metodos que se heredaron de TDALista
    @Override
    public boolean contiene(T elem) 
    { 
        return contiene(elem); 
    }

    @Override
    public int indiceDe(T elem) 
    { 
        return indiceDe(elem); 
    }

    @Override
    public T buscar(Predicate<T> criterio) 
    { 
        return buscar(criterio); 
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) 
    { 
        return ordenar(comparator); 
    }

    @Override
    public int tamano() 
    { 
        return super.tamano(); 
    }

    @Override
    public boolean esVacio() 
    { 
        return super.esVacio(); 
    }

    @Override
    public void vaciar() 
    { 
        super.vaciar(); 
    }
}