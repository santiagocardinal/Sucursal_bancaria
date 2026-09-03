package com.example.Caja_de_Herramientas.Arboles;

import java.util.function.Consumer;
import com.example.Caja_de_Herramientas.Lista.*;

// CAMBIO: implementa TElementoArbolGeneral (renombrada) en vez de
// TDAElemento, para no chocar con la interfaz del árbol binario.
public class ElementoArbolGeneralImpl<T> implements TElementoArbolGeneral<T> {

    private T dato;
    private TDALista<TElementoArbolGeneral<T>> hijos;

    public ElementoArbolGeneralImpl(T dato) {
        this.dato = dato;
        this.hijos = new ListaEnlazada<>();
    }

    @Override
    public void setDato(T dato) {
        this.dato = dato;
    }

    @Override
    public T getDato() {
        return dato;
    }

    @Override
    public TDALista<TElementoArbolGeneral<T>> getHijos() {
        return hijos;
    }

    @Override
    public void agregar(TElementoArbolGeneral<T> hijo) {
        hijos.agregar(hijo);
    }

    // FIX: acá había dos problemas: usaba una variable "hijo" no declarada,
    // y más abajo en el archivo había un segundo eliminar(Comparable<T>)
    // duplicado (no compilaba). Ahora es uno solo, por T (igualdad), que
    // busca entre los hijos (directos o más profundos) el que matchea, lo
    // saca de la lista de SU padre real y lo retorna.
    //
    // OJO: esto no elimina "this" si es this quien matchea, porque un nodo
    // no tiene referencia a su padre para sacarse solo de la lista de
    // hijos. Ese caso (borrar la raíz) lo maneja ArbolGeneralImpl, que sí
    // tiene la referencia a la raíz.
    @Override
    public TElementoArbolGeneral<T> eliminar(T criterioBusqueda) {
        for (int i = 0; i < hijos.tamano(); i++) {
            TElementoArbolGeneral<T> hijo = hijos.obtener(i);

            if (hijo.getDato().equals(criterioBusqueda)) {
                // FIX: remover por índice en vez de por objeto -- porNiveles
                // en ArbolGeneralImpl ya usa cola.remover(0), así que
                // TDALista remueve por índice y devuelve el elemento sacado.
                return hijos.remover(i);
            }

            TElementoArbolGeneral<T> enSubarbol = hijo.eliminar(criterioBusqueda);
            if (enSubarbol != null) {
                return enSubarbol;
            }
        }
        return null; // no se encontró en ningún hijo
    }

    @Override
    public boolean esHoja() {
        return hijos.esVacio();
    }

    // ---------- BÚSQUEDA ----------

    @Override
    public TElementoArbolGeneral<T> buscar(T criterioBusqueda) {
        if (this.dato.equals(criterioBusqueda)) {
            return this;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            TElementoArbolGeneral<T> encontrado = hijos.obtener(i).buscar(criterioBusqueda);
            if (encontrado != null) {
                return encontrado;
            }
        }

        return null;
    }

    // FIX: antes no existía y tiraba UnsupportedOperationException. Como
    // agregar(hijo) ya sabe sumar un hijo construido, esto es el "azúcar"
    // para insertar un dato suelto: lo envuelve en un nodo hoja y lo cuelga
    // como hijo directo de este nodo, si no existe ya en el subárbol.
    @Override
    public boolean insertar(T nuevoDato) {
        if (buscar(nuevoDato) != null) {
            return false; // ya existe
        }
        agregar(new ElementoArbolGeneralImpl<>(nuevoDato));
        return true;
    }

    // ---------- MÉTRICAS ----------

    @Override
    public int cantidadHojas() {
        if (esHoja()) {
            return 1;
        }

        int total = 0;
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadHojas();
        }
        return total;
    }

    @Override
    public int cantidadNodosInternos() {
        if (esHoja()) {
            return 0;
        }

        int total = 1; // este nodo es interno
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadNodosInternos();
        }
        return total;
    }

    @Override
    public int cantidadNodos() {
        int total = 1; // este nodo
        for (int i = 0; i < hijos.tamano(); i++) {
            total += hijos.obtener(i).cantidadNodos();
        }
        return total;
    }

    @Override
    public int altura() {
        if (esHoja()) {
            return 1;
        }

        int maxAltHijos = 0;
        for (int i = 0; i < hijos.tamano(); i++) {
            int altHijo = hijos.obtener(i).altura();
            if (altHijo > maxAltHijos) {
                maxAltHijos = altHijo;
            }
        }
        return 1 + maxAltHijos;
    }

    @Override
    public int obtenerNivel(T criterioBusqueda) {
        return obtenerNivelRecursivo(criterioBusqueda, 0);
    }

    // FIX: antes bajaba a los hijos con un cast a la clase concreta
    // ((ElementoArbolGeneralImpl<T>) hijos.obtener(i)) para poder llamar al
    // helper privado. Ahora llama al obtenerNivel público de cada hijo (ya
    // parte de la interfaz) y le suma el nivel relativo al nivel actual,
    // sin castear nada.
    private int obtenerNivelRecursivo(T criterioBusqueda, int nivelActual) {
        if (this.dato.equals(criterioBusqueda)) {
            return nivelActual;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            int nivelEnHijo = hijos.obtener(i).obtenerNivel(criterioBusqueda);
            if (nivelEnHijo != -1) {
                return nivelActual + 1 + nivelEnHijo;
            }
        }

        return -1;
    }

    // ---------- RECORRIDOS ----------

    @Override
    public void preOrder(Consumer<TElementoArbolGeneral<T>> consumidor) {
        consumidor.accept(this);
        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).preOrder(consumidor);
        }
    }

    @Override
    public void postOrder(Consumer<TElementoArbolGeneral<T>> consumidor) {
        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).postOrder(consumidor);
        }
        consumidor.accept(this);
    }

    @Override
    public void enNivel(int nivel, TDALista<TElementoArbolGeneral<T>> lista) {
        if (nivel == 0) {
            lista.agregar(this);
            return;
        }

        for (int i = 0; i < hijos.tamano(); i++) {
            hijos.obtener(i).enNivel(nivel - 1, lista);
        }
    }
}