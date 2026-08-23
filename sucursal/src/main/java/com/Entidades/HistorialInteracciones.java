package com.Entidades;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;
import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoInteraccion;

public class HistorialInteracciones {
    private Pila<Interaccion> interacciones;

    public HistorialInteracciones() {
        this.interacciones = new Pila<>();
    }

    public void registrarInteraccion(Interaccion interaccionRecibida) {
        if (interaccionRecibida == null) {
            throw new IllegalArgumentException("la interacción no puede ser nula");
        }
        this.interacciones.mete(interaccionRecibida);
    }

    public Pila<Interaccion> obtenerPorTipo(TipoInteraccion tipo) {
        return filtrar(actual -> actual.getTipo() == tipo);
    }

    public Pila<Interaccion> obtenerPorCliente(String clienteId) {
        return filtrar(actual -> actual.getClienteId().equals(clienteId));
    }

    private Pila<Interaccion> filtrar(java.util.function.Predicate<Interaccion> criterio) {
        //pilaAuxiliar: donde se va a colocar temporalmente el contenido de "interacciones", queda en orden invertido (lo más viejo termina arriba de todo).
        Pila<Interaccion> pilaAuxiliar = new Pila<>();
        // pilaResultado: acá se van a acumular únicamente los documentos que cumplen el criterio. Es lo que finalmente devuelve el método.
        Pila<Interaccion> pilaResultado = new Pila<>();

        while (!this.interacciones.esVacio()) {
            Interaccion actual = this.interacciones.saca();
            // Se lo apila en pilaAuxiliar. Como se van sacando de a uno desde el tope
            // de "interacciones" y apilando en pilaAuxiliar, el orden queda invertido:
            // el primero en salir de "interacciones" (el más nuevo) termina en el FONDO
            // de pilaAuxiliar, y el último en salir (el más viejo) termina en su TOPE ("interacciones" quedó completamente vacía y pilaAuxiliar tiene todo)
            pilaAuxiliar.mete(actual);
        }

        //  reconstruye "interacciones" y armar el resultado filtrado 
        while (!pilaAuxiliar.esVacio()) {
            Interaccion actual = pilaAuxiliar.saca();
            // Se lo vuelve a apilar en "documentos", en el mismo orden en que estaba
            // originalmente.Esto es lo que garantiza que "documentos" no queda alterada después de filtrar.
            this.interacciones.mete(actual);
            
            // Se evalúa el criterio (el Predicate) sobre el documento actual.
            if (criterio.test(actual)) {
            
                // Si cumple la condición, además se apila en pilaResultado.
                pilaResultado.mete(actual);
            }
        }
        // Se devuelve la pila con solo los documentos que cumplieron el criterio,
        // dejando intacta (mismo contenido, mismo orden) la pila "interacciones" original.
        return pilaResultado;
    }

    //consulta pedida en la letra

    // Recorre TODAS las interacciones registradas y devuelve, para cada
    // TipoInteraccion que aparezca al menos una vez, cuántas veces apareció.
    public ListaEnlazada<ConteoInteraccion> contarPorTipo() //// Complejidad: O(n * k), con n = cantidad total de interacciones y k = cantidad de tipos distintos ya vistos en ese momento
    {
        // Acá se va a ir armando el resultado. Cada ConteoInteraccion agrupa un TipoInteraccion con la cantidad de veces que apareció.
        ListaEnlazada<ConteoInteraccion> conteos = new ListaEnlazada<>();
        // pilaAuxiliar: donde se vuelca temporalmente las interacciones para poder recorrerla (igual como se hace en filtrar()).
        Pila<Interaccion> pilaAuxiliar = new Pila<>();

        // Se vacia this.interacciones volcando todo en pilaAuxiliar
        while (!this.interacciones.esVacio()) {
            Interaccion actual = this.interacciones.saca();
            pilaAuxiliar.mete(actual);
        }

        // Reconstruir las interacciones y contar por tipo
        // Al sacar de pilaAuxiliar se recorre en el orden ORIGINAL (de la más  vieja a la más nueva).
        while (!pilaAuxiliar.esVacio()) {
            Interaccion actual = pilaAuxiliar.saca();

            // Se apila de nuevo en this.interacciones para dejarla exactamente como estaba antes de llamar a este método.
            this.interacciones.mete(actual);

            // Se busca en "conteos" si ya existe una entrada para este mismo tipo
            // (por ejemplo, si ya se contó otra interacción de tipo  antes).
            ConteoInteraccion existente = conteos.buscar(c -> c.getTipo() == actual.getTipo());

            if (existente != null) {
                // Ya había una entrada para ese tipo se le suma uno al contador en vez de crear una entrada duplicada.
                existente.incrementar();
            } else {
                // Es la primera vez que aparece este tipo se crea una entrada nueva, que arranca en 1.
                conteos.agregar(new ConteoInteraccion(actual.getTipo()));
            }
        }
        //devuelve el tipo que aparece y las cantidades que aparece cada tipo, eso con cada tipo que aparezca
        return conteos;
    }

}
