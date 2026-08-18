package ucu.edu.aed.tda;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaCircular<T> implements TDALista<T> {

    private Nodo<T> cabeza;
    private Nodo<T> cola; // referencia al último nodo, para agregar al final en O(1)
    private int tamano;

    public ListaCircular() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }

    @Override
    public void agregar(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);

        if (cabeza == null) {
            // lista vacía: el nuevo nodo es cabeza y cola a la vez,
            // y se apunta a sí mismo para cerrar el círculo
            cabeza = nuevo;
            cola = nuevo;
            nuevo.setSiguiente(cabeza);
        } else {
            cola.setSiguiente(nuevo);   // el último actual pasa a apuntar al nuevo
            nuevo.setSiguiente(cabeza); // el nuevo cierra el círculo apuntando a la cabeza
            cola = nuevo;                // el nuevo pasa a ser la cola
        }

        tamano++;
    }

    @Override
    public void agregar(int index, T elem) {
        if (index < 0 || index > tamano) throw new IndexOutOfBoundsException("Índice: " + index);
        if (elem == null) throw new IllegalArgumentException("No se permiten null");

        if (index == tamano) {
            agregar(elem); // caso "insertar al final", ya resuelto arriba
            return;
        }

        Nodo<T> nuevo = new Nodo<>(elem);

        if (index == 0) {
            if (cabeza == null) {
                cabeza = nuevo;
                cola = nuevo;
                nuevo.setSiguiente(cabeza);
            } else {
                nuevo.setSiguiente(cabeza);
                cola.setSiguiente(nuevo); // la cola tiene que apuntar a la nueva cabeza
                cabeza = nuevo;
            }
        } else {
            Nodo<T> actual = cabeza;
            for (int i = 0; i < index - 1; i++) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }

        tamano++;
    }

    @Override
    public T obtener(int index) {
        if (index < 0 || index >= tamano) return null; // mismo criterio que en las otras listas

        Nodo<T> actual = cabeza;
        for (int i = 0; i < index; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }

    @Override
    public T remover(int index) {
        if (index < 0 || index >= tamano) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        T dato;

        if (index == 0) {
            dato = cabeza.getDato();

            if (tamano == 1) {
                // único elemento: la lista queda vacía
                cabeza = null;
                cola = null;
            } else {
                cabeza = cabeza.getSiguiente();
                cola.setSiguiente(cabeza); // la cola tiene que apuntar a la nueva cabeza
            }
        } else {
            Nodo<T> anterior = cabeza;
            for (int i = 0; i < index - 1; i++) {
                anterior = anterior.getSiguiente();
            }

            Nodo<T> nodoAEliminar = anterior.getSiguiente();
            dato = nodoAEliminar.getDato();

            anterior.setSiguiente(nodoAEliminar.getSiguiente());

            if (nodoAEliminar == cola) {
                cola = anterior; // si removimos la cola, el anterior pasa a serlo
            }
        }

        tamano--;
        return dato;
    }

    @Override
    public boolean remover(T elem) {
        if (cabeza == null || elem == null) return false;

        int indice = indiceDe(elem);
        if (indice == -1) return false;

        remover(indice);
        return true;
    }

    @Override
    public boolean contiene(T elem) {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) {
        if (elem == null || cabeza == null) return -1;

        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (actual.getDato().equals(elem)) return i;
            actual = actual.getSiguiente();
        }
        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) {
        if (criterio == null || cabeza == null) return null;

        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            if (criterio.test(actual.getDato())) return actual.getDato();
            actual = actual.getSiguiente();
        }
        return null;
    }

    @Override
    public TDALista<T> ordenar(Comparator<T> comparator) {
        ListaCircular<T> resultado = new ListaCircular<>();

        Nodo<T> actual = cabeza;
        for (int i = 0; i < tamano; i++) {
            resultado.agregar(actual.getDato());
            actual = actual.getSiguiente();
        }

        if (resultado.cabeza == null) return resultado;

        Nodo<T> i = resultado.cabeza;
        for (int pos = 0; pos < resultado.tamano; pos++) {
            Nodo<T> menor = i;
            Nodo<T> j = i.getSiguiente();

            for (int k = pos + 1; k < resultado.tamano; k++) {
                if (comparator.compare(j.getDato(), menor.getDato()) < 0) {
                    menor = j;
                }
                j = j.getSiguiente();
            }

            T aux = i.getDato();
            i.setDato(menor.getDato());
            menor.setDato(aux);

            i = i.getSiguiente();
        }

        return resultado;
    }

    @Override
    public int tamano() {
        return tamano;
    }

    @Override
    public boolean esVacio() {
        return cabeza == null;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        cola = null;
        tamano = 0;
    }
}
