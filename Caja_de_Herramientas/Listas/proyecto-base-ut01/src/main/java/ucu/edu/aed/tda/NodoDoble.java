package ucu.edu.aed.tda;

public class NodoDoble <T> extends Nodo<T>{

    private Nodo<T> anterior;

    public NodoDoble (T dato){
        super(dato);
        this.anterior = null;
    }

    public Nodo<T> getAnterior(){
        return this.anterior;
    }

    public void setAnterior(Nodo<T> anterior){
        this.anterior = anterior;
    }
}
