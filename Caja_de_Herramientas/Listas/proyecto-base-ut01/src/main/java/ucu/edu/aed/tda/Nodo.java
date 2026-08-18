package ucu.edu.aed.tda;

public class Nodo <T> {

    private T dato;
    private Nodo<T> siguiente;
    private Nodo<T> anterior;

    public Nodo (T dato){
        this.dato = dato;
        this.siguiente = null;
        this.anterior = null;
    }

    public T getDato(){
        return this.dato;
    }
    public void setDato(T dato){
        this.dato = dato;
    }
    
    public Nodo<T> getSiguiente(){
        return this.siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente){
        this.siguiente = siguiente;
    }

    public Nodo<T> getAnterior(){
        return this.anterior;
    }

    public void setAnterior(Nodo<T> anterior){
        this.anterior = anterior;
    }
}



