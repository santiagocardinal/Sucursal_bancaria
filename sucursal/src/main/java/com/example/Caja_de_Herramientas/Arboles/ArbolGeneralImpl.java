package com.example.Caja_de_Herramientas.Arboles;

public class ArbolGeneralImpl<T> {

    private TDAElemento<T> raiz;

    public boolean esVacio() {
        return raiz == null;
    }

    public T getRaiz() {
        return raiz == null ? null : raiz.getDato();
    }

    // ---------- INSERCIÓN ----------

    public void insertarRaiz(T dato) {
        raiz = new ElementoArbolGeneralImpl<>(dato);
    }

    public boolean insertarHijo(T datoPadre, T datoHijo) {
        if (raiz == null) return false;

        TDAElemento<T> padre = raiz.buscar(datoPadre);
        if (padre == null) return false;

        padre.agregarHijo(new ElementoArbolGeneralImpl<>(datoHijo));
        return true;
    }

    // ---------- BÚSQUEDA ----------

    public boolean buscar(T dato) {
        if (raiz == null) return false;
        return raiz.buscar(dato) != null;
    }

    // ---------- ELIMINACIÓN (elimina el nodo y todo su subárbol) ----------

    public boolean eliminar(T dato) {
        if (raiz == null) return false;

        if (raiz.getDato().equals(dato)) {
            raiz = null;
            return true;
        }

        return eliminarRecursivo(raiz, dato);
    }

    private boolean eliminarRecursivo(TDAElemento<T> nodo, T dato) {
        TDALista<TDAElemento<T>> hijos = nodo.getHijos();

        for (int i = 0; i < hijos.tamano(); i++) {
            TDAElemento<T> hijo = hijos.obtener(i);

            if (hijo.getDato().equals(dato)) {
                return nodo.eliminarHijo(hijo);
            }

            if (eliminarRecursivo(hijo, dato)) {
                return true;
            }
        }

        return false;
    }

    // ---------- RECORRIDOS ----------

    public void preorden(Consumer<TDAElemento<T>> consumidor) {
        if (raiz != null) raiz.preOrder(consumidor);
    }

    public void postorden(Consumer<TDAElemento<T>> consumidor) {
        if (raiz != null) raiz.postOrder(consumidor);
    }

    public void porNiveles(Consumer<TDAElemento<T>> consumidor) {
        if (raiz == null) return;

        TDALista<TDAElemento<T>> cola = new ListaEnlazada<>();
        cola.agregar(raiz);

        while (!cola.esVacio()) {
            TDAElemento<T> actual = cola.remover(0);
            consumidor.accept(actual);

            TDALista<TDAElemento<T>> hijos = actual.getHijos();
            for (int i = 0; i < hijos.tamano(); i++) {
                cola.agregar(hijos.obtener(i));
            }
        }
    }

    // ---------- MÉTRICAS DEL ÁRBOL ----------

    public int altura() {
        return raiz == null ? 0 : raiz.altura();
    }

    public int cantidadNodos() {
        return raiz == null ? 0 : raiz.cantidadNodos();
    }

    public int cantidadHojas() {
        return raiz == null ? 0 : raiz.cantidadHojas();
    }

    public int cantidadNodosInternos() {
        return raiz == null ? 0 : raiz.cantidadNodosInternos();
    }

    public int obtenerNivel(T dato) {
        return raiz == null ? -1 : raiz.obtenerNivel(dato);
    }
}
