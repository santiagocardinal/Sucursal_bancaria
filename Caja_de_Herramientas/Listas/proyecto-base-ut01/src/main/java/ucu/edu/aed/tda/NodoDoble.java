package ucu.edu.aed.tda;

public class NodoDoble<T> extends Nodo<T>{

    private NodoDoble<T> anterior;

    public NodoDoble(T dato) {
        super(dato);
        this.anterior = null;
    }

    public void setAnterior(NodoDoble<T> anterior) {
        this.anterior = anterior;
    }

    public NodoDoble<T> getAnterior(){
        return this.anterior;
    }

}

