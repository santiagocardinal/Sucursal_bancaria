package com.example.Caja_de_Herramientas.Arboles;

public class AVLImpl<T> extends ABBImpl<T> {
    
    
    @Override
    public boolean insertar(Comparable<T> dato) {
        raiz = insertarAVL(raiz, dato);
        return true;
    }

    private TDAElemento<T> insertarAVL(TDAElemento<T> nodo, Comparable<T> dato) {

        // Caso base
        if (nodo == null) {
            return new ElementoABBImpl<>((T) dato);
        }

        // Caso recursivo - posicionar igual que ABB
        int cmp = dato.compareTo(nodo.getDato());

        if (cmp < 0) {
            nodo.setHijoIzquierdo(insertarAVL(nodo.getHijoIzquierdo(), dato));
        } else if (cmp > 0) {
            nodo.setHijoDerecho(insertarAVL(nodo.getHijoDerecho(), dato));
        } else {
            return nodo; // duplicado
        }

        // Calcular balance del nodo
        int altIzq = (nodo.getHijoIzquierdo() == null) ? 0 : nodo.getHijoIzquierdo().altura();
        int altDer = (nodo.getHijoDerecho() == null) ? 0 : nodo.getHijoDerecho().altura();
        int balance = altIzq - altDer;

        // Caso LL
        if (balance > 1 && dato.compareTo(nodo.getHijoIzquierdo().getDato()) < 0)
            return rotacionLL(nodo);

        // Caso LR
        if (balance > 1 && dato.compareTo(nodo.getHijoIzquierdo().getDato()) > 0)
            return rotacionLR(nodo);

        // Caso RR
        if (balance < -1 && dato.compareTo(nodo.getHijoDerecho().getDato()) > 0)
            return rotacionRR(nodo);

        // Caso RL
        if (balance < -1 && dato.compareTo(nodo.getHijoDerecho().getDato()) < 0)
            return rotacionRL(nodo);

        return nodo; // nodo está balanceado
    }

    // CASO 1 - LL
    private TDAElemento<T> rotacionLL(TDAElemento<T> k2) {
        TDAElemento<T> k1 = k2.getHijoIzquierdo();
        k2.setHijoIzquierdo(k1.getHijoDerecho());
        k1.setHijoDerecho(k2);
        return k1;
    }

    // CASO 4 - RR
    private TDAElemento<T> rotacionRR(TDAElemento<T> k1) {
        TDAElemento<T> k2 = k1.getHijoDerecho();
        k1.setHijoDerecho(k2.getHijoIzquierdo());
        k2.setHijoIzquierdo(k1);
        return k2;
    }

    // CASO 2 - LR
    private TDAElemento<T> rotacionLR(TDAElemento<T> k3) {
        k3.setHijoIzquierdo(rotacionRR(k3.getHijoIzquierdo()));
        return rotacionLL(k3);
    }

    // CASO 3 - RL
    private TDAElemento<T> rotacionRL(TDAElemento<T> k1) {
        k1.setHijoDerecho(rotacionLL(k1.getHijoDerecho()));
        return rotacionRR(k1);
    }

    @Override
    public boolean eliminar(Comparable<T> criterio) {
        if (raiz == null) return false;
        if (raiz.buscar(criterio) == null) return false;
        raiz = eliminarAVL(raiz, criterio);
        return true;
    }
    private TDAElemento<T> eliminarAVL(TDAElemento<T> nodo, Comparable<T> criterio) {

    if (nodo == null) return null;

    int cmp = criterio.compareTo(nodo.getDato());

    if (cmp < 0) {
        nodo.setHijoIzquierdo(eliminarAVL(nodo.getHijoIzquierdo(), criterio));
    } else if (cmp > 0) {
        nodo.setHijoDerecho(eliminarAVL(nodo.getHijoDerecho(), criterio));
    } else {
        nodo = nodo.eliminar(criterio); // nodo ahora apunta al sucesor
    }

    if (nodo == null) return null; // era hoja, se eliminó sin reemplazo

    // balance sobre el nodo correcto (el sucesor si fue reemplazado)
    int altIzq = (nodo.getHijoIzquierdo() == null) ? 0 : nodo.getHijoIzquierdo().altura();
    int altDer = (nodo.getHijoDerecho() == null) ? 0 : nodo.getHijoDerecho().altura();
    int balance = altIzq - altDer;

    // rotación basada en alturas, no en criterio
    if (balance > 1) {
        int altIzqIzq = (nodo.getHijoIzquierdo().getHijoIzquierdo() == null) ? 0
                : nodo.getHijoIzquierdo().getHijoIzquierdo().altura();
        int altIzqDer = (nodo.getHijoIzquierdo().getHijoDerecho() == null) ? 0
                : nodo.getHijoIzquierdo().getHijoDerecho().altura();

        if (altIzqIzq >= altIzqDer)
            return rotacionLL(nodo);
        else
            return rotacionLR(nodo);
    }

    if (balance < -1) {
        int altDerDer = (nodo.getHijoDerecho().getHijoDerecho() == null) ? 0
                : nodo.getHijoDerecho().getHijoDerecho().altura();
        int altDerIzq = (nodo.getHijoDerecho().getHijoIzquierdo() == null) ? 0
                : nodo.getHijoDerecho().getHijoIzquierdo().altura();

        if (altDerDer >= altDerIzq)
            return rotacionRR(nodo);
        else
            return rotacionRL(nodo);
    }

    return nodo;
    }

    /*private TDAElemento<T> eliminarAVL(TDAElemento<T> nodo, Comparable<T> criterio) {

        // Caso base
        if (nodo == null) return null;

        // Caso recursivo
        TDAElemento<T> tmp = nodo; // nodo de referencia

        if (criterio.compareTo(nodo.getDato()) < 0) {
            nodo.setHijoIzquierdo(eliminarAVL(nodo.getHijoIzquierdo(), criterio));

        } else if (criterio.compareTo(nodo.getDato()) > 0) {
            nodo.setHijoDerecho(eliminarAVL(nodo.getHijoDerecho(), criterio));

        } else {
            // en tmp queda el mayor del hijo izquierdo
            tmp = nodo.eliminar(criterio);
        }

        
        // Calcular balance del nodo
        int altIzq = (nodo.getHijoIzquierdo() == null) ? 0 : nodo.getHijoIzquierdo().altura();
        int altDer = (nodo.getHijoDerecho() == null) ? 0 : nodo.getHijoDerecho().altura();
        int balance = altIzq - altDer;

        // Caso LL
        if (balance > 1 && criterio.compareTo(nodo.getHijoIzquierdo().getDato()) < 0)
            return rotacionLL(nodo);

        // Caso LR
        if (balance > 1 && criterio.compareTo(nodo.getHijoIzquierdo().getDato()) > 0)
            return rotacionLR(nodo);

        // Caso RR
        if (balance < -1 && criterio.compareTo(nodo.getHijoDerecho().getDato()) > 0)
            return rotacionRR(nodo);

        // Caso RL
        if (balance < -1 && criterio.compareTo(nodo.getHijoDerecho().getDato()) < 0)
            return rotacionRL(nodo);


        return tmp; // nodo está balanceado
    }
    //===========================NUEVO=========================
    /*@Override
    public TDALista<TDAElemento<T>> enNivel(int nivel) {
        if (raiz == null) return new ListaImpl<>();
        return raiz.enNivel(nivel);
    }*/
}
