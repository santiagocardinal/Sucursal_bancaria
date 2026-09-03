package com.example.Caja_de_Herramientas.Arboles;

// NOTA: esta es la versión que ya te había corregido antes (el flag
// insercionExitosa para que insertar() devuelva false en duplicados, y el
// fix del borrado con dos hijos para que se rebalancee bien), solo
// renombrada de TElementoAB a TDAElemento. El AVLImpl que pegaste en el
// mensaje anterior era la versión vieja SIN esos fixes -- si en tu repo
// ya tenés cambios propios encima de esa versión vieja, avisame para
// mezclarlos, porque acá te piso con esta.
public class AVLImpl<T> extends ABBImpl<T> {

    // FIX: antes insertar() devolvía siempre true, aunque el dato fuera
    // duplicado y no se insertara nada. Uso este flag para que insertarAVL
    // pueda "avisarle" a insertar() si realmente hubo una inserción.
    private boolean insercionExitosa;

    @Override
    public boolean insertar(Comparable<T> dato) {
        insercionExitosa = true;
        raiz = insertarAVL(raiz, dato);
        return insercionExitosa;
    }

    @SuppressWarnings("unchecked")
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
            insercionExitosa = false; // FIX: duplicado -> no se insertó nada, insertar() debe devolver false
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

    // NOTA: ninguna de las 4 rotaciones actualiza una altura "cacheada" en
    // el nodo, y no hace falta: ElementoABBImpl.altura() la recalcula
    // recorriendo el subárbol cada vez, no la guarda en un campo, así que
    // después de una rotación el próximo altura() ya lee la estructura
    // nueva sola. (Si algún día cambian ElementoABBImpl para cachearla,
    // ahí sí habría que actualizarla acá en cada rotación.)

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
            // FIX (bug principal): antes esto era simplemente
            //   nodo = nodo.eliminar(criterio);
            // que delega la baja en el método de ABB. El problema es que,
            // cuando el nodo tiene dos hijos, ABB busca el sucesor y lo
            // saca del subárbol derecho SIN pasar por eliminarAVL, así que
            // ningún nivel de ese camino recalculaba altura ni rotaba: el
            // árbol podía quedar desbalanceado después de un borrado y
            // nadie lo corregía.
            //
            // Ahora: para hoja o un solo hijo, no hay ningún subárbol
            // "por debajo" que se modifique, así que sigue alcanzando con
            // nodo.eliminar(). Para dos hijos, buscamos el sucesor acá
            // mismo y lo eliminamos del subárbol derecho llamando de nuevo
            // a eliminarAVL, para que el rebalanceo se propague por todo
            // el camino recorrido. Reutilizamos el nodo existente con
            // setDato() en vez de crear uno nuevo.
            if (nodo.getHijoIzquierdo() != null && nodo.getHijoDerecho() != null) {
                TDAElemento<T> sucesor = nodo.getHijoDerecho();
                while (sucesor.getHijoIzquierdo() != null) {
                    sucesor = sucesor.getHijoIzquierdo();
                }
                T datoSucesor = sucesor.getDato();
                nodo.setDato(datoSucesor);
                nodo.setHijoDerecho(eliminarAVL(nodo.getHijoDerecho(), (Comparable<T>) datoSucesor));
            } else {
                nodo = nodo.eliminar(criterio); // hoja o un solo hijo: esto sigue estando bien
            }
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
}