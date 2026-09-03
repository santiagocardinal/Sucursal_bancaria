package com.example.Caja_de_Herramientas.Arboles;

import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.TDALista;

public class Elemento implements TDAElemento<T> 
{
    private T dato;
    private TDAElemento<T> hijoIzquierdo;
    private TDAElemento<T> hijoDerecho;

    public Elemento(T dato) 
    {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    @Override
    public void setHijoIzquierdo(TDAElemento<T> hijoIzquierdo) 
    {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    @Override
    public void setHijoDerecho(TDAElemento<T> hijoDerecho) 
    {
        this.hijoDerecho = hijoDerecho;
    }

    @Override
    public TDAElemento<T> getHijoIzquierdo() 
    {
        return hijoIzquierdo;
    }

    @Override
    public TDAElemento<T> getHijoDerecho() 
    {
        return hijoDerecho;
    }

    @Override
    public void setDato(T dato) 
    {
        this.dato = dato;
    }

    @Override
    public T getDato() 
    {
        return dato;
    }

    @Override
    public boolean esHoja()
    {
        return (getHijoIzquierdo() == null && getHijoDerecho() == null);
    }

    @Override
    public boolean insertar(Comparable<T> nuevoDato)
    {
        int compare = nuevoDato.compareTo(this.dato);
        if(compare == 0)
        {
            return false;
        }
        else if(compare < 0 )
        {
            if(hijoIzquierdo == null)
            {
                hijoIzquierdo = new Elemento<>((T) nuevoDato);
                return true;
            }
            else
            {
                return hijoIzquierdo.insertar(nuevoDato);
            }
        }
        else 
        {
            if (hijoDerecho == null)
            {
                hijoDerecho = new Elemento<>((T) nuevoDato);
                return true;
            }
            else
            {
                return hijoDerecho.insertar(nuevoDato);
            }
        }     
    } 

    @Override
    public TDAElemento<T> buscar(Comparable<T> criterioBusqueda)
    {
        int compare = criterioBusqueda.compareTo(this.dato);
        if(compare == 0)
        {
            return this;
        }
        else if(compare < 0 )
        {
            if(hijoIzquierdo == null)
            {
                return null;
            }
            else
            {
                return hijoIzquierdo.buscar(criterioBusqueda);
            }
        }
        else 
        {
            if (hijoDerecho == null)
            {
                return null;
            }
            else
            {
                return hijoDerecho.buscar(criterioBusqueda);
            }
        }    
    }
    
    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor)
    {
        consumidor.accept(this);
        if(hijoIzquierdo != null)
        {
            hijoIzquierdo.preOrder(consumidor);
        }
        if (hijoDerecho != null)
        {
            hijoDerecho.preOrder(consumidor);
        }
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor)
    {
        if(hijoIzquierdo != null)
        {
            hijoIzquierdo.postOrder(consumidor);
        }
        if (hijoDerecho != null)
        {
            hijoDerecho.postOrder(consumidor);
        }
        consumidor.accept(this);
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor)
    {
        if(hijoIzquierdo != null)
        {
            hijoIzquierdo.inOrder(consumidor);
        }
        consumidor.accept(this);
        if (hijoDerecho != null)
        {
            hijoDerecho.inOrder(consumidor);
        }
    }

    @Override
    public int cantidadHojas()
    {
        if (esHoja())
        {
            return 1;
        }

        int izq = 0;
        if (hijoIzquierdo != null)
        {
            izq = hijoIzquierdo.cantidadHojas();
        }

        int der = 0;
        if (hijoDerecho != null)
        {
            der = hijoDerecho.cantidadHojas();
        }

        return izq + der;
    }

    @Override
    public int cantidadNodosInternos()
    {
        if (esHoja())
        {
            return 0;
        }

        int izq = 0;
        if (hijoIzquierdo != null)
        {
            izq = hijoIzquierdo.cantidadNodosInternos();
        }

        int der = 0;
        if (hijoDerecho != null)
        {
            der = hijoDerecho.cantidadNodosInternos();
        }

        return 1 + izq + der;
    }

    @Override
    public int cantidadNodos()
    {
        int izq = 0;
        if (hijoIzquierdo != null)
        {
            izq = hijoIzquierdo.cantidadNodos();
        }

        int der = 0;
        if (hijoDerecho != null)
        {
            der = hijoDerecho.cantidadNodos();
        }

        return 1 + izq + der;
    }

    @Override
    public int altura()
    {
        int alturaIzq = -1;
        if (hijoIzquierdo != null)
        {
            alturaIzq = hijoIzquierdo.altura();
        }

        int alturaDer = -1;
        if (hijoDerecho != null)
        {
            alturaDer = hijoDerecho.altura();
        }

        return 1 + Math.max(alturaIzq, alturaDer);
    }

    @Override
    public int obtenerNivel(Comparable<T> criterioBusqueda)
    {
        int compare = criterioBusqueda.compareTo(this.dato);
        if (compare == 0)
        {
            return 0;
        }
        if (compare < 0)
        {
            if (hijoIzquierdo == null)
            {
                return -1;
            }
            int nivel = hijoIzquierdo.obtenerNivel(criterioBusqueda);
            if (nivel == -1)
            {
                return -1;
            }
            return nivel + 1;
        }
        else
        {
            if (hijoDerecho == null)
            {
                return -1;
            }
            int nivel = hijoDerecho.obtenerNivel(criterioBusqueda);
            if (nivel == -1)
            {
                return -1;
            }
            return nivel + 1;
        }
    }

    @Override
    public TDAElemento<T> eliminar(Comparable<T> criterioBusqueda)
    {
        if (hijoIzquierdo != null && criterioBusqueda.compareTo(hijoIzquierdo.getDato()) == 0)
        {
            TDAElemento<T> eliminado = hijoIzquierdo;
            hijoIzquierdo = reemplazar(hijoIzquierdo);
            return eliminado;
        }
        if (hijoDerecho != null && criterioBusqueda.compareTo(hijoDerecho.getDato()) == 0)
        {
            TDAElemento<T> eliminado = hijoDerecho;
            hijoDerecho = reemplazar(hijoDerecho);
            return eliminado;
        }

        int compare = criterioBusqueda.compareTo(this.dato);
        if (compare < 0 && hijoIzquierdo != null)
        {
            return hijoIzquierdo.eliminar(criterioBusqueda);
        }
        if (compare > 0 && hijoDerecho != null)
        {
            return hijoDerecho.eliminar(criterioBusqueda);
        }
        return null;
    }

    private TDAElemento<T> reemplazar(TDAElemento<T> nodo)
    {
        if (nodo.getHijoIzquierdo() == null)
        {
            return nodo.getHijoDerecho();
        }
        if (nodo.getHijoDerecho() == null)
        {
            return nodo.getHijoIzquierdo();
        }

        TDAElemento<T> sucesor = nodo.getHijoDerecho();
        while (sucesor.getHijoIzquierdo() != null)
        {
            sucesor = sucesor.getHijoIzquierdo();
        }
        T datoSucesor = sucesor.getDato();
        nodo.eliminar((Comparable<T>) datoSucesor);
        nodo.setDato(datoSucesor);
        return nodo;
    }

    @Override
    public boolean insertar(T nuevoDato) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insertar'");
    }

    @Override
    public void inOrder(Consumer<TDAElemento<T>> consumidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inOrder'");
    }

    @Override
    public void preOrder(Consumer<TDAElemento<T>> consumidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preOrder'");
    }

    @Override
    public void postOrder(Consumer<TDAElemento<T>> consumidor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'postOrder'");
    }

    @Override
    public void completos(TDALista<TDAElemento<T>> lista) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completos'");
    }

    @Override
    public void enNivel(int nivel, TDALista<TDAElemento<T>> lista) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enNivel'");
    }
}
