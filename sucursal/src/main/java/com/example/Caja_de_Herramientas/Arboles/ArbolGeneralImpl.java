package com.example.Caja_de_Herramientas.Arboles;

import java.util.function.Consumer;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Lista.TDALista;

// CAMBIO: usa TElementoArbolGeneral (renombrada) en vez de TDAElemento.
public class ArbolGeneralImpl<T> {

    private TElementoArbolGeneral<T> raiz;

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

    // FIX: llamaba a padre.agregarHijo(...), que no existe -- el método de
    // la interfaz se llama agregar(...).
    public boolean insertarHijo(T datoPadre, T datoHijo) {
        if (raiz == null) return false;

        TElementoArbolGeneral<T> padre = raiz.buscar(datoPadre);
        if (padre == null) return false;

        padre.agregar(new ElementoArbolGeneralImpl<>(datoHijo));
        return true;
    }

    // ---------- BÚSQUEDA ----------

    public boolean buscar(T dato) {
        if (raiz == null) return false;
        return raiz.buscar(dato) != null;
    }

    // ---------- ELIMINACIÓN ----------

    // FIX: tenía un eliminarRecursivo(nodo, dato) propio que llamaba a
    // nodo.eliminarHijo(hijo) (no existe) reimplementando a mano algo que
    // TElementoArbolGeneral.eliminar(T) ya hace (busca entre los hijos,
    // directos o más profundos, y saca el que matchea). Ahora el único
    // caso especial acá es la raíz (porque un nodo no puede sacarse solo
    // de ningún lado); todo lo demás se delega directo en raiz.eliminar(dato).
    public boolean eliminar(T dato) {
        if (raiz == null) return false;

        if (raiz.getDato().equals(dato)) {
            raiz = null;
            return true;
        }

        return raiz.eliminar(dato) != null;
    }

    // ---------- RECORRIDOS ----------

    public void preorden(Consumer<TElementoArbolGeneral<T>> consumidor) {
        if (raiz != null) raiz.preOrder(consumidor);
    }

    public void postorden(Consumer<TElementoArbolGeneral<T>> consumidor) {
        if (raiz != null) raiz.postOrder(consumidor);
    }

    public void porNiveles(Consumer<TElementoArbolGeneral<T>> consumidor) {
        if (raiz == null) return;

        TDALista<TElementoArbolGeneral<T>> cola = new ListaEnlazada<>();
        cola.agregar(raiz);

        while (!cola.esVacio()) {
            TElementoArbolGeneral<T> actual = cola.remover(0);
            consumidor.accept(actual);

            TDALista<TElementoArbolGeneral<T>> hijos = actual.getHijos();
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