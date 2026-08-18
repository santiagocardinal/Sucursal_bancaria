package com.example.Caja_de_Herramientas.Cola;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import com.example.Caja_de_Herramientas.Lista.TDALista;
import com.example.Caja_de_Herramientas.Lista.Nodo;

public class ColaPrioridad<T> implements TDACola<T> 
{
    private NodoPrioridad<T> frente;
    private int tamano;

    public ColaPrioridad() 
    {
        this.frente = null;
        this.tamano = 0;
    }


    public boolean poneEnCola(T dato, int prioridad) 
    {
        if (dato == null) return false;

        NodoPrioridad<T> nuevo = new NodoPrioridad<>(dato, prioridad);

        if (frente == null || frente.getPrioridad() < prioridad) 
        {
            nuevo.setSiguiente(frente); //el nuevo apunta al antiguo frente
            frente = nuevo;             //el nuevo pasa a ser el prente
            tamano++;                   
            return true;
        }

        Nodo<T> actual = frente; 

        while (actual.getSiguiente() != null && ((NodoPrioridad<T>) actual.getSiguiente()).getPrioridad() >= prioridad) 
        {
            actual = actual.getSiguiente();
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
        frente = (NodoPrioridad<T>) frente.getSiguiente();
        tamano--;
        return dato;
    }

    @Override
    public boolean poneEnCola(T dato) 
    {
        return poneEnCola(dato, 0); //prioridad default 0
    }

    //@Override
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
        if (index < 0) return null;
        Nodo<T> actual = frente;
        int i = 0;
        while (actual != null) 
        {
            if (i == index) return actual.getDato();
            actual = actual.getSiguiente();
            i++;
        }
        return null;
    }

    /*@Override
    public void agregar(T elem) 
    {
        return poneEnCola(elem, 0);
    }*/

    @Override
    public void agregar(int index, T elem) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
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

    //@Override
    public T quitar(T elemento) 
    {
        if (frente == null || elemento == null) return null;
        Nodo<T> actual = frente;
        Nodo<T> anterior = null;
        while (actual != null) 
        {
            if (actual.getDato().equals(elemento)) 
            {
                if (anterior == null) frente = (NodoPrioridad<T>) actual.getSiguiente();
                else anterior.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(null);
                tamano--;
                return actual.getDato();
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

    //@Override
    public boolean eliminar(T elemento) 
    {
        return quitar(elemento) != null;
    }

    //@Override
    public T quitar(int indice) 
    {
        T elem = obtener(indice);
        if (elem == null) return null;
        return quitar(elem);
    }

    //@Override
    public boolean eliminar(int indice) 
    {
        return quitar(indice) != null;

    }
//==========================AUNQUE SEA UNA LISTA CON PRIORIDAD NO SE PUEDEN HACER LAS SIGUIENTES COSAS======================
//ESTA EL METODO COMO PARA CUMPLIR CON LA IMPLEMNTACION PERO EN REALIDAD INSERVIBLE=========================================
    //@Override
    public TDALista<T> invertir() 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }

    //@Override
    public TDALista<T> ordenarTotal(Comparator<T> comparator) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }

    //@Override
    public TDALista<T> concatenar(TDALista<T> otra) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }

    //@Override
    public TDALista<T> intercalar(TDALista<T> otra) 
    {
        throw new UnsupportedOperationException("No aplica en cola con prioridad");
    }


    @Override
    public T remover(int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }


    @Override
    public boolean remover(T elem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }


    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ordenar'");
    }


    @Override
    public void agregar(T elem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregar'");
    }
}
