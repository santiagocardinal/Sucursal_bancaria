package com.example.Caja_de_Herramientas.Lista;

import java.util.Comparator;
import java.util.function.Predicate;

public class ListaDoblementeEnlazada<T> implements TDALista<T> {

    private NodoDoble<T> cabeza; // |1|<->|2|
    private NodoDoble<T> anterior; // referencia al de adelante y al de atras
    private int tamano;

    public ListaDoblementeEnlazada() {
        cabeza = null;
        anterior = null;
        tamano = 0;
    }

    @Override
    public void agregar(T elem) {

        if (elem == null) {
            throw new IllegalArgumentException("No se permiten null");
        }

        NodoDoble<T> nuevo = new NodoDoble<>(elem);

        if (cabeza == null) 
        {
            cabeza = nuevo;
            anterior = nuevo;
        } 
        else 
        {
            anterior.setSiguiente(nuevo);
            nuevo.setAnterior(anterior);
            anterior = nuevo;

            //|1|<->|2|<->|3|<->|4|
            // |anterior|<->|nuevo|
            //|dato|<->|anterior|
        }

        tamano++;
    }

    @Override
    public void agregar(int indice, T elemento) 
    {
        if (indice < 0 || indice > tamano) 
            throw new IndexOutOfBoundsException("Índice: " + indice); //no se puede acceder al indice

        if (elemento == null) 
            throw new IllegalArgumentException("No se permiten null"); //no se puede tener un elemento nulo

        NodoDoble<T> nuevo = new NodoDoble<>(elemento);

        if (indice == 0) 
        {
            // insertar al inicio
            nuevo.setSiguiente(cabeza); //|nuevo|<->|cabeza|

            if (cabeza != null) 
            {
                cabeza.setAnterior(nuevo); //la cabeza es un elemento distinto de nulo, el anterior a la cabeza va a ser nuevo, mueve uno para adelante y deja espacio para insertar
            }

            cabeza = nuevo;

            if (tamano == 0) 
            {
                anterior = nuevo; // si lista vacía, el nuevo también es el último
            }
        }
        else if (indice == tamano) 
        {
            // insertar al final
            nuevo.setAnterior(anterior);

            if (anterior != null) 
            {
                anterior.setSiguiente(nuevo);
            }

            anterior = nuevo;
        } 
        else 
        {
            NodoDoble<T> actual = cabeza;

            // recorrer hasta el nodo anterior a la posición deseada
            for (int i = 0; i < indice - 1; i++) 
            {
                actual = (NodoDoble<T>) actual.getSiguiente();            
            }

            NodoDoble<T> siguiente = 
                (NodoDoble<T>) actual.getSiguiente();

            nuevo.setSiguiente(siguiente);
            nuevo.setAnterior(actual);

            actual.setSiguiente(nuevo);
            siguiente.setAnterior(nuevo);
        }

        tamano++;
    }

    @Override
    public T obtener(int indice) 
    {
        if (indice < 0 || indice >= tamano)
        {
            throw new IndexOutOfBoundsException("Índice: " + indice);
        }

        NodoDoble<T> nuevo = cabeza; //se crea un nuevo nodo el cual se le asigna ser la "cabeza"
        int i = 0; //control de variable

        while (nuevo != null) //mientras que el nodo siguiente sea distinto de nulo
        {
            if (i == indice) 
            {
                return nuevo.getDato(); //y si i(variable de control) es igual al indice pasado por parametro, devolver el dato que se encuentra en ese indice
            }

            nuevo = (NodoDoble<T>) nuevo.getSiguiente(); //va pasando al siguiente
            i++;
        }

        throw new IndexOutOfBoundsException("Índice: " + indice);
    }

    // Método auxiliar privado: dado un nodo YA localizado dentro de la lista,
    // lo desconecta actualizando los punteros de sus vecinos, la cabeza,
    // la cola (campo 'anterior') y el contador 'tamano'. Devuelve su dato.
    private T desconectar(NodoDoble<T> nodo) {

        NodoDoble<T> nodoAnterior = 
            (NodoDoble<T>) nodo.getAnterior();

        NodoDoble<T> nodoSiguiente = 
            (NodoDoble<T>) nodo.getSiguiente();

        // Si 'nodo' tenía algo antes, ese algo pasa a apuntar a lo que
        // seguía después de 'nodo'. Si no tenía nada antes, 'nodo' era
        // la cabeza, así que la nueva cabeza pasa a ser el siguiente.
        if (nodoAnterior != null) {
            nodoAnterior.setSiguiente(nodoSiguiente);
        } 
        else {
            cabeza = nodoSiguiente;
        }

        // Simétricamente para el lado de "siguiente": si había algo
        // después, ese algo pasa a apuntar hacia atrás al anterior de
        // 'nodo'. Si no había nada después, 'nodo' era el último, así
        // que actualizamos el puntero de cola.
        if (nodoSiguiente != null) {
            nodoSiguiente.setAnterior(nodoAnterior);
        } 
        else {
            anterior = nodoAnterior; // 'anterior' acá cumple el rol de cola/último
        }

        // Desvinculamos el nodo removido para no dejar referencias colgantes
        nodo.setSiguiente(null);
        nodo.setAnterior(null);

        tamano--;

        return nodo.getDato();
    }

    @Override
    public T remover(int index) {

        if (index < 0 || index >= tamano) {
            throw new IndexOutOfBoundsException("Índice: " + index);
        }

        // Recorremos con el mismo patrón que 'obtener'
        NodoDoble<T> actual = cabeza;
        int i = 0;

        while (actual != null && i < index) {
            actual = (NodoDoble<T>) actual.getSiguiente();
            i++;
        }

        return desconectar(actual);
    }

    @Override
    public boolean remover(T elem) {

        if (cabeza == null || elem == null) return false;

        NodoDoble<T> actual = cabeza;

        while (actual != null) {

            if (actual.getDato().equals(elem)) {
                desconectar(actual);
                return true; // se encontró y se removió
            }

            actual = (NodoDoble<T>) actual.getSiguiente();
        }

        return false; // se recorrió toda la lista y no estaba
    }

    @Override
    public boolean contiene(T elem) 
    {
        return indiceDe(elem) != -1;
    }

    @Override
    public int indiceDe(T elem) //PERFECTISIMO
    {
        if (elem == null) return -1;

        NodoDoble<T> nuevo = cabeza;
        int indice = 0;

        while (nuevo != null) 
        {
            if (nuevo.getDato().equals(elem)) 
            {
                return indice;
            }

            nuevo = (NodoDoble<T>) nuevo.getSiguiente();
            indice++;
        }

        return -1;
    }

    @Override
    public T buscar(Predicate<T> criterio) 
    {
        if (criterio == null)
        {
            throw new IllegalArgumentException(
                "El criterio no puede ser null"
            );
        }

        NodoDoble<T> temp = cabeza;

        while (temp != null) 
        {
            if (criterio.test(temp.getDato())) 
            {
                return temp.getDato(); //DAME INFORMACION O ALGO SOBRE ESTO
            }

            temp = (NodoDoble<T>) temp.getSiguiente();
        }

        return null;
    }

    // La interfaz pide que devuelva una lista NUEVA y ordenada, sin
    // modificar esta. Por eso primero se copian los datos y despues se
    // ordena la copia (Selection Sort).
    @Override
    public TDALista<T> ordenar(Comparator<T> comp)
    {
        if (comp == null)
        {
            throw new IllegalArgumentException(
                "El comparator no puede ser null"
            );
        }

        ListaDoblementeEnlazada<T> resultado =
            new ListaDoblementeEnlazada<>();

        // copiamos la lista original
        NodoDoble<T> actual = cabeza;

        while (actual != null)
        {
            resultado.agregar(actual.getDato());
            actual = (NodoDoble<T>) actual.getSiguiente();
        }

        // Selection Sort sobre la copia
        NodoDoble<T> nuevo = resultado.cabeza;

        while (nuevo != null) 
        {
            NodoDoble<T> menor = nuevo;

            NodoDoble<T> temp = 
                (NodoDoble<T>) nuevo.getSiguiente();

            while (temp != null) 
            {
                if (comp.compare(
                        temp.getDato(),
                        menor.getDato()) < 0)
                {
                    menor = temp;
                }

                temp = 
                    (NodoDoble<T>) temp.getSiguiente();
            }

            if (menor != nuevo)
            {
                T aux = nuevo.getDato();

                nuevo.setDato(menor.getDato());
                menor.setDato(aux);
            }

            nuevo = 
                (NodoDoble<T>) nuevo.getSiguiente();
        }

        return resultado;
    }

    @Override
    public int tamano() //DIVINOOOOO :)
    {
        return tamano;
    }

    @Override
    public boolean esVacio() {
        return cabeza == null;
    }

    @Override
    public void vaciar() {
        cabeza = null;
        anterior = null;
        tamano = 0;
    }
}