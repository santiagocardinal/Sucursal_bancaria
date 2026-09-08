package com.example.Caja_de_Herramientas.Arboles;

import com.example.Enums.NivelPrioridad;

import java.util.NoSuchElementException;

// Cola con prioridad implementada con un monticulo (heap) binario.
// A diferencia de ColaPrioridad (que usa una lista enlazada ordenada
// y cuesta O(n) insertar), este monticulo inserta y extrae en O(log n).
public class MonticuloPrioridad<T> implements TDAMonticuloPrioridad<T> {

    // Clase interna chica para guardar, en cada posicion del array,
    // el dato junto con su nivel de prioridad numerico.
    // No necesita puntero a "siguiente": en un heap las relaciones
    // padre/hijo se calculan con la posicion en el array, no con nodos.
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

    private static final int capacidadInicial = 10;

    private Object[] datos;   // array interno: representa el arbol del heap
    private int cantidad;     // cantidad de elementos realmente ocupados

    public MonticuloPrioridad() {
        // arrancamos con un array vacio de capacidad inicial, igual que ListaArray
        this.datos = new Object[capacidadInicial];
        this.cantidad = 0;
    }

    // ---------- Metodos para calcular posiciones en el arbol ----------
    // Estas cuentas son la esencia del heap: reemplazan a los punteros
    // que usariamos si fuera una estructura enlazada.

    private int padre(int indice) {
        // el padre de cualquier nodo esta en (indice - 1) / 2
        return (indice - 1) / 2;
    }

    private int hijoIzquierdo(int indice) {
        // el hijo izquierdo esta en 2*indice + 1
        return 2 * indice + 1;
    }

    private int hijoDerecho(int indice) {
        // el hijo derecho esta en 2*indice + 2
        return 2 * indice + 2;
    }

    // ---------- Redimensionar (igual criterio que ListaArray) ----------

    private void asegurarCapacidad() {
        if (cantidad == datos.length) {
            // si el array se llenó, lo duplicamos, copiando lo que ya había
            Object[] nuevoArray = new Object[datos.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevoArray[i] = datos[i];
            }
            datos = nuevoArray;
        }
    }

    // ---------- Único lugar donde se hace el casteo ----------
    // Java no permite crear arrays de tipos genéricos (no se puede hacer
    // new Elemento<T>[10]), por eso el array interno es de Object.
    // En vez de castear "a mano" en cada método, lo hacemos una sola vez
    // acá, y el resto de la clase llama a este método en vez de castear.
    @SuppressWarnings("unchecked")
    private Elemento<T> obtener(int indice) {
        return (Elemento<T>) datos[indice];
    }

    // ---------- Intercambiar dos posiciones del array ----------

    private void intercambiar(int i, int j) {
        Object temporal = datos[i];
        datos[i] = datos[j];
        datos[j] = temporal;
    }

    // ---------- Reacomodar hacia arriba (usado al insertar) ----------

    private void subir(int indice) {
        // mientras el elemento no sea la raiz (indice 0)...
        while (indice > 0) {
            int indicePadre = padre(indice);
            Elemento<T> actual = obtener(indice);
            Elemento<T> padreActual = obtener(indicePadre);

            // si el actual tiene MAS urgencia (menor valor numerico) que su padre,
            // hay que intercambiarlos para mantener la propiedad del heap
            if (actual.getPrioridad() < padreActual.getPrioridad()) {
                intercambiar(indice, indicePadre);
                indice = indicePadre; // seguimos subiendo desde la nueva posicion
            } else {
                break; // ya está bien ubicado, paramos
            }
        }
    }

    // ---------- Reacomodar hacia abajo (usado al extraer el minimo) ----------

    private void bajar(int indice) {
        while (true) {
            int izq = hijoIzquierdo(indice);
            int der = hijoDerecho(indice);
            int masUrgente = indice; // por ahora, asumimos que el actual está bien

            // si el hijo izquierdo existe y es más urgente que el actual, lo marcamos
            if (izq < cantidad &&
                obtener(izq).getPrioridad() < obtener(masUrgente).getPrioridad()) {
                masUrgente = izq;
            }

            // lo mismo con el hijo derecho, comparando contra el más urgente hasta ahora
            if (der < cantidad &&
                obtener(der).getPrioridad() < obtener(masUrgente).getPrioridad()) {
                masUrgente = der;
            }

            // si ninguno de los hijos es más urgente que el actual, ya está bien ubicado
            if (masUrgente == indice) {
                break;
            }

            // si no, intercambiamos y seguimos bajando desde la nueva posicion
            intercambiar(indice, masUrgente);
            indice = masUrgente;
        }
    }

    // ---------- Buscar la posición de un dato dentro del array ----------
    // A diferencia de un ABB, en un heap NO hay ningún criterio de orden
    // que permita "saltear" ramas al buscar: el único dato garantizado
    // es que la raíz es el mínimo. Por eso, buscar un elemento cualquiera
    // es siempre O(n): hay que recorrer todo el array en el peor caso.
    private int buscarIndice(T dato) {
        for (int i = 0; i < cantidad; i++) {
            if (obtener(i).getDato().equals(dato)) {
                return i;
            }
        }
        return -1; // no está en el montículo
    }

    // ---------- Metodos de TDAMonticuloPrioridad ----------

    @Override
    public boolean modificarPrioridad(T dato, NivelPrioridad nuevaPrioridad) {
        if (dato == null || nuevaPrioridad == null) {
            return false;
        }

        int indice = buscarIndice(dato);
        if (indice == -1) {
            return false; // el cliente no está esperando en este montículo
        }

        Elemento<T> actual = obtener(indice);
        int prioridadVieja = actual.getPrioridad();
        int prioridadNueva = nuevaPrioridad.getValor();

        // reemplazamos el elemento por uno nuevo, con la prioridad actualizada
        // (los campos de Elemento son final, así que no se puede "editar in place")
        datos[indice] = new Elemento<>(actual.getDato(), prioridadNueva);

        if (prioridadNueva < prioridadVieja) {
            // se volvió MÁS urgente (valor numérico más chico): puede que
            // ahora le gane a su padre, así que intentamos subirlo
            subir(indice);
        } else if (prioridadNueva > prioridadVieja) {
            // se volvió MENOS urgente (valor numérico más grande): puede que
            // ahora sus hijos le ganen a él, así que intentamos bajarlo
            bajar(indice);
        }
        // si la prioridad nueva es igual a la vieja, no hace falta reacomodar nada

        return true;
    }

    // ---------- Resto de metodos de TDAMonticuloPrioridad ----------

    @Override
    public boolean poneEnCola(T dato, NivelPrioridad prioridad) {
        if (dato == null || prioridad == null) {
            return false;
        }

        asegurarCapacidad();

        // el nuevo elemento entra siempre al final del array...
        Elemento<T> nuevo = new Elemento<>(dato, prioridad.getValor());
        datos[cantidad] = nuevo;

        // ...y despues "sube" hasta la posicion que le corresponda
        subir(cantidad);

        cantidad++;
        return true;
    }

    @Override
    public boolean poneEnCola(T dato) {
        // sin prioridad explícita, usamos NORMAL por defecto (mismo criterio que ColaPrioridad)
        return poneEnCola(dato, NivelPrioridad.NORMAL);
    }

    @Override
    public T frente() {
        if (esVacio()) {
            throw new NoSuchElementException("El monticulo esta vacio");
        }
        // la raiz del heap (posicion 0) siempre es el elemento mas urgente
        return obtener(0).getDato();
    }

    @Override
    public T quitaDeCola() {
        if (esVacio()) {
            throw new NoSuchElementException("El monticulo esta vacio");
        }

        Elemento<T> raiz = obtener(0); // este es el que vamos a devolver

        cantidad--;
        datos[0] = datos[cantidad]; // el ultimo elemento pasa a ocupar la raiz
        datos[cantidad] = null;     // limpiamos la posicion que quedo libre

        if (cantidad > 0) {
            bajar(0); // reacomodamos desde la raiz hacia abajo
        }

        return raiz.getDato();
    }

    @Override
    public void anula() {
        datos = new Object[capacidadInicial];
        cantidad = 0;
    }

    @Override
    public int tamano() {
        return cantidad;
    }

    @Override
    public boolean esVacio() {
        return cantidad == 0;
    }

    @Override
    public void vaciar() {
        anula();
    }

    //@Override
    public int posicionDe(T elemento) {
        if (elemento == null) {
            return -1;
        }

        int indiceElemento = buscarIndice(elemento);
        if (indiceElemento == -1) {
            return -1; // no está en el montículo
        }

        int prioridadBuscada = obtener(indiceElemento).getPrioridad();
        int contador = 0;

        for (int i = 0; i < cantidad; i++) {
            if (obtener(i).getPrioridad() < prioridadBuscada) 
            {
                contador++;
            }
        }
        return contador;
    }
}