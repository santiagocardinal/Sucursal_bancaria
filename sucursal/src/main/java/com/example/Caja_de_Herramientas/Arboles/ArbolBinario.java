package com.example.Caja_de_Herramientas.Arboles;
import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.*;

// FIX: sacado "implements Comparable<T>" -- no hace falta, obligaba a un
// compareTo(T) que solo tiraba excepción. Lo que se compara es el dato
// que guarda cada nodo, vía los Comparable<T> que ya reciben
// insertar/buscar/eliminar.
public class ArbolBinario<T> implements TDAArbolBinario<T>
{
    private TDAElemento<T> raiz;
    private int contador;
    private int contadorBusquedas;

    public ArbolBinario()
    {
        this.raiz = null;
        this.contador = 0;
        this.contadorBusquedas = 0;
    }

    @Override
    public boolean esVacio()
    {
        return raiz == null;
    }

    @Override
    public TDAElemento<T> obtenerRaiz()
    {
        return raiz;
    }

    // FIX: tomaba "T dato" pero TDAArbolBinario.insertar declara
    // Comparable<T> dato -- eso ni compilaba contra la interfaz. Y aunque
    // hubiese compilado, adentro raiz.insertar(dato) con un T terminaba
    // resolviendo al overload por T de Elemento (el que tira excepción) en
    // vez del real por Comparable<T>. Con la firma corregida ese problema
    // desaparece solo, porque ya no hay ambigüedad de overload.
    @Override
    @SuppressWarnings("unchecked")
    public boolean insertar(Comparable<T> dato)
    {
        boolean insertado;

        if (esVacio())
        {
            raiz = new Elemento<>((T) dato);
            insertado = true;
        }
        else
        {
            insertado = raiz.insertar(dato);
        }

        if (insertado)
        {
            contador++;
        }

        return insertado;
    }

    @Override
    public T buscar(Comparable<T> criterioBusqueda)
    {
        contadorBusquedas++;

        if (esVacio())
        {
            return null;
        }
        TDAElemento<T> buscado = raiz.buscar(criterioBusqueda);
        if (buscado == null)
        {
            return null;
        }
        else
        {
            return buscado.getDato();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean eliminar(Comparable<T> criterioBusqueda)
    {
        if (esVacio())
        {
            return false;
        }

        if (criterioBusqueda.compareTo(raiz.getDato()) == 0)
        {
            if (raiz.getHijoIzquierdo() == null)
            {
                raiz = raiz.getHijoDerecho();
            }
            else if (raiz.getHijoDerecho() == null)
            {
                raiz = raiz.getHijoIzquierdo();
            }
            else
            {
                TDAElemento<T> sucesor = raiz.getHijoDerecho();
                while (sucesor.getHijoIzquierdo() != null)
                {
                    sucesor = sucesor.getHijoIzquierdo();
                }
                T datoSucesor = sucesor.getDato();
                raiz.eliminar((Comparable<T>) datoSucesor);
                raiz.setDato(datoSucesor);
            }
            return true;
        }
        return raiz.eliminar(criterioBusqueda) != null;
    }

    @Override
    public void preOrder(Consumer<T> consumidor)
    {
        if (!esVacio())
        {
            raiz.preOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void inOrder(Consumer<T> consumidor)
    {
        if (!esVacio())
        {
            raiz.inOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public void postOrder(Consumer<T> consumidor)
    {
        if (!esVacio())
        {
            raiz.postOrder(nodo -> consumidor.accept(nodo.getDato()));
        }
    }

    @Override
    public int cantidadNodos()
    {
        if (esVacio())
        {
            return 0;
        }
        return raiz.cantidadNodos();
    }

    @Override
    public int cantidadHojas()
    {
        if (esVacio())
        {
            return 0;
        }
        return raiz.cantidadHojas();
    }

    @Override
    public int cantidadNodosInternos()
    {
        if (esVacio())
        {
            return 0;
        }
        return raiz.cantidadNodosInternos();
    }

    public int getContador()
    {
        return contador;
    }

    public int getContadorBusquedas()
    {
        return contadorBusquedas;
    }

    public String preOrderString()
    {
        if (esVacio())
        {
            return "el arbol esta vacio";
        }
        StringBuilder string = new StringBuilder();
        raiz.preOrder(nodo -> { string.append(nodo.getDato()).append(" "); });
        return string.toString();
    }

    public String postOrderString()
    {
        if (esVacio())
        {
            return "el arbol esta vacio";
        }
        StringBuilder string = new StringBuilder();
        raiz.postOrder(nodo -> { string.append(nodo.getDato()).append(" "); });
        return string.toString();
    }

    public String inOrderString()
    {
        if (esVacio())
        {
            return "el arbol esta vacio";
        }
        StringBuilder string = new StringBuilder();
        raiz.inOrder(nodo -> { string.append(nodo.getDato()).append(" "); });
        return string.toString();
    }

    // FIX: estos tres tiraban UnsupportedOperationException. Ya tenías
    // todo lo necesario (raiz.altura()/completos()/enNivel() ya existen en
    // TDAElemento) -- solo faltaba llamarlos.
    @Override
    public int altura()
    {
        return esVacio() ? 0 : raiz.altura();
    }

    @Override
    public TDALista<TDAElemento<T>> completos()
    {
        TDALista<TDAElemento<T>> lista = new ListaEnlazada<>();
        if (!esVacio())
        {
            raiz.completos(lista);
        }
        return lista;
    }

    @Override
    public TDALista<TDAElemento<T>> enNivel(int nivel)
    {
        TDALista<TDAElemento<T>> lista = new ListaEnlazada<>();
        if (!esVacio())
        {
            raiz.enNivel(nivel, lista);
        }
        return lista;
    }
}