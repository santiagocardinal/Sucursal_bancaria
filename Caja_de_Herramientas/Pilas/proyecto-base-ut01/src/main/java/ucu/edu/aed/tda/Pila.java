package ucu.edu.aed.tda;

import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.function.Predicate;
import ucu.edu.aed.tda.TDALista;

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