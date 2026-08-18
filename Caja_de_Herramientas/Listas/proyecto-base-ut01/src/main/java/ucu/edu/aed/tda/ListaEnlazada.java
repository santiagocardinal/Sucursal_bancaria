package ucu.edu.aed.tda;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaEnlazada<T> implements TDALista<T> {

    private Nodo<T> cabeza;

    public ListaEnlazada() {
        cabeza = null;
    }

    @Override
    public void agregar(T elem) {
        Nodo<T> nuevo = new Nodo<T>(elem);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            Nodo<T> temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0) throw new IndexOutOfBoundsException("Índice: " + index);
        if (elem == null) throw new IllegalArgumentException("No se permiten null");

        Nodo<T> nuevo = new Nodo<>(elem);
        if (index == 0) {
            nuevo.setSiguiente(cabeza);
            cabeza = nuevo;
            return;
        }

        Nodo<T> actual = cabeza;
        int contador = 0;

        while (actual != null) {
            // cuando estamos en index - 1
            if (contador == index - 1) {
                nuevo.setSiguiente(actual.getSiguiente());
                actual.setSiguiente(nuevo);
                return;
            }
            actual = actual.getSiguiente();
            contador++;
        }
        // Si salimos del while, el índice no existe
        throw new IndexOutOfBoundsException("Índice: " + index);
    }

    @Override
    public T obtener(int index) {
        Nodo<T> actual = cabeza;
        int i = 0;

        while (actual != null) {
            if (i == index) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
            i++;
        }

        throw new IndexOutOfBoundsException("Índice: " + index);
    }

    @Override
    public T remover(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        // Caso: cabeza
        if (index == 0) {
            if (cabeza == null) {
                throw new IndexOutOfBoundsException("Índice: " + index);
            }
            T dato = cabeza.getDato();
            Nodo<T> aux = cabeza;
            cabeza = cabeza.getSiguiente();
            aux.setSiguiente(null); // desvincular
            return dato;
        }

        Nodo<T> anterior = cabeza;
        int i = 0;

        while (anterior != null && i < index - 1) {
            anterior = anterior.getSiguiente();
            i++;
        }

        if (anterior == null || anterior.getSiguiente() == null) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        Nodo<T> nodoAEliminar = anterior.getSiguiente();
        anterior.setSiguiente(nodoAEliminar.getSiguiente());
        nodoAEliminar.setSiguiente(null); // desvincular

        return nodoAEliminar.getDato();
    }

    @Override
    public boolean remover(T elem) {
        if (cabeza == null || elem == null) {
            return false;
        }

        Nodo<T> actual = cabeza;
        Nodo<T> anterior = null;

        while (actual != null) {
            if (actual.getDato().equals(elem)) {
                if (anterior == null) {
                    cabeza = actual.getSiguiente();
                } else {
                    anterior.setSiguiente(actual.getSiguiente());
                }

                actual.setSiguiente(null); // desvincular
                return true;
            }

            anterior = actual;
            actual = actual.getSiguiente();
        }

        return false;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        if (elem == null) {
            return -1;
        }
        Nodo<T> actual = cabeza;
        int indice = 0;
        while (actual != null) {
            if (actual.getDato().equals(elem)) {
                return indice;
            }
            actual = actual.getSiguiente();
            indice++;
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        if (criterio == null) return null;
        Nodo<T> temp = cabeza;

        while (temp != null) {
            if (criterio.test(temp.getDato())) {
                return temp.getDato();
            }
            temp = temp.getSiguiente();
        }
        return null;
    }

    // La interfaz pide que devuelva una lista NUEVA y ordenada, sin
    // modificar esta. Por eso primero se copian los datos y despues se
    // ordena la copia (Selection Sort).
    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaEnlazada<T> resultado = new ListaEnlazada<>();

        Nodo<T> actual = cabeza;
        while (actual != null) {
            resultado.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }

        if (resultado.cabeza == null) return resultado;

        Nodo<T> i = resultado.cabeza;
        while (i != null) {
            Nodo<T> menor = i;
            Nodo<T> j = i.getSiguiente();

            while (j != null) {
                if (comparator.compare(j.getDato(), menor.getDato()) < 0) {
                    menor = j;
                }
                j = j.getSiguiente();
            }

            // swap
            T aux = i.getDato();
            i.setDato(menor.getDato());
            menor.setDato(aux);

            i = i.getSiguiente();
        }

        return resultado;
    }

    @Override
    public int tamaño() {
        int contador = 0;
        Nodo<T> actual = cabeza;

        while (actual != null) {
            contador++;
            actual = actual.getSiguiente();
        }

        return contador;
    }

    @Override
    public boolean esVacio() {
        return cabeza == null;
    }

    @Override
    public void vaciar() {
        cabeza = null;
    }

    // Inserta 'elem' manteniendo el orden de la lista según el criterio
    // que define 'comparator'. Asume que la lista YA está ordenada según <-
    // ese mismo comparator antes de llamar a este método.
    public void insertarOrdenado(T elem, Comparator<T> comparator) {

        // Validamos que no nos pasen un elemento null, porque no podríamos
        // compararlo contra los demás nodos.
        if (elem == null) throw new IllegalArgumentException("No se permiten null");

        // Creamos el nuevo nodo que vamos a insertar en algún lugar de la lista.
        Nodo<T> nuevo = new Nodo<>(elem);

        // Caso 1: la lista está vacía, o el nuevo elemento debe ir ANTES
        // que la cabeza actual (es decir, es "menor" según el comparator).
        // En ambos casos el nuevo nodo pasa a ser la nueva cabeza.
        if (cabeza == null || comparator.compare(elem, cabeza.getDato()) < 0) { // < / > /<= / >= DEPENDE DEL CRITERIO
            nuevo.setSiguiente(cabeza); // el nuevo apunta a lo que era la cabeza
            cabeza = nuevo;             // el nuevo pasa a ser la cabeza
            return;                     // terminamos, ya insertamos
        }

        // Caso 2: recorremos la lista buscando el lugar donde insertar.
        // 'actual' es el nodo desde el cual vamos mirando hacia adelante.
        Nodo<T> actual = cabeza; // |1|->|2|->|3|->|4|->|5|->|6|

        // Avanzamos mientras haya un siguiente nodo Y ese siguiente nodo
        // sea "menor o igual" que el elemento a insertar (es decir, mientras
        // el elemento todavía no deba insertarse antes de 'actual.getSiguiente()').
        while (actual.getSiguiente() != null
                && comparator.compare(actual.getSiguiente().getDato(), elem) <= 0) {
            actual = actual.getSiguiente(); // seguimos avanzando
        }

        // Al salir del while, 'actual' es el nodo justo ANTES de donde
        // debe quedar el nuevo elemento (porque actual.getSiguiente() es
        // null, o es mayor que elem).
        nuevo.setSiguiente(actual.getSiguiente()); // el nuevo apunta a lo que seguía
        actual.setSiguiente(nuevo);                // 'actual' ahora apunta al nuevo
    }
}

