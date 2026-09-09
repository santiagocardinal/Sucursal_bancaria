package com.example.Caja_de_Herramientas.Arboles;

import com.example.Caja_de_Herramientas.Lista.ListaArray;
import com.example.Enums.NivelPrioridad;

import java.util.NoSuchElementException;

// Cola con prioridad implementada con un monticulo (heap) binario.
// A diferencia de ColaPrioridad (que usa una lista enlazada ordenada
// y cuesta O(n) insertar), este monticulo inserta y extrae en O(log n).
//
// El "arbol" del heap se guarda dentro de una ListaArray<Elemento<T>>,
// reutilizando la estructura ya hecha en el primer hito. Las relaciones
// padre/hijo se calculan con la posicion dentro de esa lista (no con
// punteros), aprovechando que el heap siempre es un arbol completo.
public class MonticuloPrioridad<T> implements TDAMonticuloPrioridad<T> {

    // Clase interna chica para guardar, en cada posicion de la lista,
    // el dato junto con su nivel de prioridad numerico.
    private static class Elemento<T> {
        private final T dato;
        private final int prioridad;

        Elemento(T dato, int prioridad) {
            this.dato = dato;
            this.prioridad = prioridad;
        }

        T getDato() {
            return dato;
        }

        int getPrioridad() {
            return prioridad;
        }
    }

    private ListaArray<Elemento<T>> datos; // el "arbol" del heap, guardado en la lista

    public MonticuloPrioridad() {
        this.datos = new ListaArray<>();
    }

    // ---------- Metodos para calcular posiciones en el arbol ----------
    // Estas cuentas son la esencia del heap: reemplazan a los punteros
    // que usariamos si fuera una estructura enlazada.

    private int padre(int indice) {
        return (indice - 1) / 2;
    }

    private int hijoIzquierdo(int indice) {
        return 2 * indice + 1;
    }

    private int hijoDerecho(int indice) {
        return 2 * indice + 2;
    }

    // ---------- Intercambiar dos posiciones ----------
    // Gracias a reemplazar(index, elem) (agregado a ListaArray), esto
    // sigue siendo O(1): no desplaza ningun otro elemento de la lista.
    private void intercambiar(int i, int j) {
        Elemento<T> temporal = datos.obtener(i);
        datos.reemplazar(i, datos.obtener(j));
        datos.reemplazar(j, temporal);
    }

    // ---------- Reacomodar hacia arriba (usado al insertar) ----------

    private void subir(int indice) {
        while (indice > 0) {
            int indicePadre = padre(indice);
            Elemento<T> actual = datos.obtener(indice);
            Elemento<T> padreActual = datos.obtener(indicePadre);

            if (actual.getPrioridad() < padreActual.getPrioridad()) {
                intercambiar(indice, indicePadre);
                indice = indicePadre;
            } else {
                break;
            }
        }
    }

    // ---------- Reacomodar hacia abajo (usado al extraer el minimo) ----------

    private void bajar(int indice) {
        while (true) {
            int izq = hijoIzquierdo(indice);
            int der = hijoDerecho(indice);
            int masUrgente = indice;

            if (izq < datos.tamano() &&
                datos.obtener(izq).getPrioridad() < datos.obtener(masUrgente).getPrioridad()) {
                masUrgente = izq;
            }

            if (der < datos.tamano() &&
                datos.obtener(der).getPrioridad() < datos.obtener(masUrgente).getPrioridad()) {
                masUrgente = der;
            }

            if (masUrgente == indice) {
                break;
            }

            intercambiar(indice, masUrgente);
            indice = masUrgente;
        }
    }

    // ---------- Buscar la posición de un dato dentro de la lista ----------
    private int buscarIndice(T dato) {
        for (int i = 0; i < datos.tamano(); i++) {
            if (datos.obtener(i).getDato().equals(dato)) {
                return i;
            }
        }
        return -1;
    }

    // ---------- Metodos de TDAMonticuloPrioridad ----------

    @Override
    public boolean poneEnCola(T dato, NivelPrioridad prioridad) {
        if (dato == null || prioridad == null) {
            return false;
        }

        Elemento<T> nuevo = new Elemento<>(dato, prioridad.getValor());
        datos.agregar(nuevo);
        subir(datos.tamano() - 1);

        return true;
    }

    @Override
    public boolean poneEnCola(T dato) {
        return poneEnCola(dato, NivelPrioridad.NORMAL);
    }

    @Override
    public T frente() {
        if (esVacio()) {
            throw new NoSuchElementException("El monticulo esta vacio");
        }
        return datos.obtener(0).getDato();
    }

    @Override
    public T quitaDeCola() {
        if (esVacio()) {
            throw new NoSuchElementException("El monticulo esta vacio");
        }

        Elemento<T> raiz = datos.obtener(0);
        int ultimoIndice = datos.tamano() - 1;

        if (ultimoIndice == 0) {
            datos.remover(0);
            return raiz.getDato();
        }

        datos.reemplazar(0, datos.obtener(ultimoIndice));
        datos.remover(ultimoIndice);

        bajar(0);

        return raiz.getDato();
    }

    @Override
    public boolean modificarPrioridad(T dato, NivelPrioridad nuevaPrioridad) {
        if (dato == null || nuevaPrioridad == null) {
            return false;
        }

        int indice = buscarIndice(dato);
        if (indice == -1) {
            return false;
        }

        Elemento<T> actual = datos.obtener(indice);
        int prioridadVieja = actual.getPrioridad();
        int prioridadNueva = nuevaPrioridad.getValor();

        datos.reemplazar(indice, new Elemento<>(actual.getDato(), prioridadNueva));

        if (prioridadNueva < prioridadVieja) {
            subir(indice);
        } else if (prioridadNueva > prioridadVieja) {
            bajar(indice);
        }

        return true;
    }

    //@Override
    public int posicionDe(T elemento) {
        if (elemento == null) {
            return -1;
        }

        int indiceElemento = buscarIndice(elemento);
        if (indiceElemento == -1) {
            return -1;
        }

        int prioridadBuscada = datos.obtener(indiceElemento).getPrioridad();
        int contador = 0;

        for (int i = 0; i < datos.tamano(); i++) {
            if (datos.obtener(i).getPrioridad() < prioridadBuscada) {
                contador++;
            }
        }

        return contador;
    }

    @Override
    public void anula() {
        datos.vaciar();
    }

    @Override
    public int tamano() {
        return datos.tamano();
    }

    @Override
    public boolean esVacio() {
        return datos.esVacio();
    }

    @Override
    public void vaciar() {
        anula();
    }
}