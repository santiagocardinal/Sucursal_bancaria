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

        return filtrar(
            documentoActual ->
                documentoActual.getCliente().getCi().equals(cliente.getCi())
        );
    }

    public Pila<Documento> obtenerPorTipo(TipoDocumento tipo) {

        if (tipo == null) {
            throw new IllegalArgumentException("El tipo no puede ser nulo");
        }

        return filtrar(
            documentoActual -> documentoActual.getTipo() == tipo
        );
    }

    private Pila<Documento> filtrar(
        java.util.function.Predicate<Documento> criterio) {

        Pila<Documento> pilaAuxiliar = new Pila<>();
        Pila<Documento> pilaResultado = new Pila<>();

        while (!documentos.esVacio()) {

            Documento actual = documentos.saca();

            pilaAuxiliar.mete(actual);
        }

        while (!pilaAuxiliar.esVacio()) {

            Documento actual = pilaAuxiliar.saca();

            documentos.mete(actual);

            if (criterio.test(actual)) {
                pilaResultado.mete(actual);
            }
        }

        return pilaResultado;
    }

    public Documento obtenerUltimoRegistrado() 
    {
        if (documentos.esVacio()) 
        {
            return null;
        }
        return documentos.tope();
    }

    //consulta
    
    public Pila<Documento> obtenerVencidosPorCliente(Cliente cliente) 
    {
        if (cliente == null) 
        {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        return filtrar(documentoActual -> documentoActual.getCliente().getCi().equals(cliente.getCi()) && !documentoActual.estaVigente());
    }
}