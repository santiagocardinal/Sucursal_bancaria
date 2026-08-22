package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Enums.TipoDocumento;

public class DocumentoTest {

    @Test
    public void crearDocumentoCorrectamente() {

        Cliente cliente = new Cliente("12345678");

        Documento documento = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente
        );

        assertEquals("DOC001", documento.getId());
        assertEquals(TipoDocumento.CONTRATO, documento.getTipo());
        assertEquals(cliente, documento.getCliente());
    }

    @Test(expected = NullPointerException.class)
    public void noPermiteIdNulo() {

        Cliente cliente = new Cliente("12345678");

        new Documento(
            null,
            TipoDocumento.CONTRATO,
            cliente
        );
    }

    @Test(expected = NullPointerException.class)
    public void noPermiteTipoNulo() {

        Cliente cliente = new Cliente("12345678");

        new Documento(
            "DOC001",
            null,
            cliente
        );
    }

    @Test(expected = NullPointerException.class)
    public void noPermiteClienteNulo() {

        new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            null
        );
    }
}