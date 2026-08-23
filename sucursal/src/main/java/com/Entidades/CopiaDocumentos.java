package com.Entidades;

import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoDocumento;

public class CopiaDocumentos {

    private Pila<Documento> documentos;

    public CopiaDocumentos() {
        this.documentos = new Pila<>();
    }

    public void registrarDocumento(Documento documento) {

        if (documento == null) {
            throw new IllegalArgumentException("El documento no puede ser nulo");
        }

        documentos.mete(documento);
    }

    public Pila<Documento> obtenerPorCliente(Cliente cliente) {

        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        return filtrar(documentoActual ->documentoActual.getCliente().getCi().equals(cliente.getCi())
        );
    }

    public Pila<Documento> obtenerPorTipo(TipoDocumento tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo no puede ser nulo");
        }

        return filtrar(documentoActual -> documentoActual.getTipo() == tipo
        );
    }

  
    private Pila<Documento> filtrar(java.util.function.Predicate<Documento> criterio) {

        // pilaAuxiliar: donde se va a colocar temporalmente el contenido de "documentos", queda en orden invertido (lo más viejo termina arriba de todo).
        Pila<Documento> pilaAuxiliar = new Pila<>();
        // pilaResultado: acá se van a acumular únicamente los documentos que cumplen el criterio. Es lo que finalmente devuelve el método.
        Pila<Documento> pilaResultado = new Pila<>();

        while (!documentos.esVacio()) {

            Documento actual = documentos.saca();
            // Se lo apila en pilaAuxiliar. Como se van sacando de a uno desde el tope
            // de "documentos" y apilando en pilaAuxiliar, el orden queda invertido:
            // el primero en salir de "documentos" (el más nuevo) termina en el FONDO
            // de pilaAuxiliar, y el último en salir (el más viejo) termina en su TOPE ("documentos" quedó completamente vacía y pilaAuxiliar tiene todo)
            pilaAuxiliar.mete(actual);
        }

        //  reconstruye "documentos" y armar el resultado filtrado 
        while (!pilaAuxiliar.esVacio()) {

            Documento actual = pilaAuxiliar.saca();

            // Se lo vuelve a apilar en "documentos", en el mismo orden en que estaba
            // originalmente.Esto es lo que garantiza que "documentos" no queda alterada después de filtrar.
            documentos.mete(actual);

            // Se evalúa el criterio (el Predicate) sobre el documento actual.
            if (criterio.test(actual)) {
                // Si cumple la condición, además se apila en pilaResultado.
                pilaResultado.mete(actual);
            }
        }
        // Se devuelve la pila con solo los documentos que cumplieron el criterio,
        // dejando intacta (mismo contenido, mismo orden) la pila "documentos" original.
        return pilaResultado;
    }

    // Devuelve el último documento que se registró en la sucursal (el más reciente),
    // o null si todavía no se registró ninguno.
    public Documento obtenerUltimoRegistrado() 
    {
        if (documentos.esVacio()) 
        {
            return null;
        }
        return documentos.tope();
    }

    //consulta pedida en la letra
    
    public Pila<Documento> obtenerVencidosPorCliente(Cliente cliente) 
    {
        if (cliente == null) 
        {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        //Se queda con el documento solo si es del cliente indicado y además está vencido (Vencido el documento — es decir, ya pasó su fecha de vigencia).
        return filtrar(documentoActual -> documentoActual.getCliente().getCi().equals(cliente.getCi()) && !documentoActual.estaVigente());
    }
}