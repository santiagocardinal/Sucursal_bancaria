package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.Caja_de_Herramientas.Pila.Pila;
import com.example.Enums.TipoDocumento;

public class CopiaDocumentosTest {

    @Test
    public void registrarDocumentoCorrectamente() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente = new Cliente("12345678");

        Documento documento = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente
        );

        copiaDocumentos.registrarDocumento(documento);

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorCliente(cliente);

        assertEquals(1, resultado.tamano());
        assertEquals(documento, resultado.tope());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noPermiteRegistrarDocumentoNulo() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        copiaDocumentos.registrarDocumento(null);
    }

    @Test
    public void obtenerPorClienteDevuelveSoloDocumentosDelCliente() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente1 = new Cliente("11111111");
        Cliente cliente2 = new Cliente("22222222");

        Documento doc1 = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente1
        );

        Documento doc2 = new Documento(
            "DOC002",
            TipoDocumento.PAGARE,
            cliente2
        );

        Documento doc3 = new Documento(
            "DOC003",
            TipoDocumento.CEDULA_IDENTIDAD,
            cliente1
        );

        copiaDocumentos.registrarDocumento(doc1);
        copiaDocumentos.registrarDocumento(doc2);
        copiaDocumentos.registrarDocumento(doc3);

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorCliente(cliente1);

        assertEquals(2, resultado.tamano());

        while (!resultado.esVacio()) {

            Documento documento = resultado.saca();

            assertEquals(
                "11111111",
                documento.getCliente().getCi()
            );
        }
    }

    @Test
    public void obtenerPorClienteSinCoincidenciasDevuelvePilaVacia() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente1 = new Cliente("11111111");
        Cliente cliente2 = new Cliente("22222222");

        Documento documento = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente1
        );

        copiaDocumentos.registrarDocumento(documento);

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorCliente(cliente2);

        assertTrue(resultado.esVacio());
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenerPorClienteNoPermiteClienteNulo() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        copiaDocumentos.obtenerPorCliente(null);
    }

    @Test
    public void obtenerPorTipoDevuelveSoloDocumentosDelTipo() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente = new Cliente("12345678");

        Documento doc1 = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente
        );

        Documento doc2 = new Documento(
            "DOC002",
            TipoDocumento.PAGARE,
            cliente
        );

        Documento doc3 = new Documento(
            "DOC003",
            TipoDocumento.CONTRATO,
            cliente
        );

        copiaDocumentos.registrarDocumento(doc1);
        copiaDocumentos.registrarDocumento(doc2);
        copiaDocumentos.registrarDocumento(doc3);

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorTipo(
                TipoDocumento.CONTRATO
            );

        assertEquals(2, resultado.tamano());

        while (!resultado.esVacio()) {

            Documento documento = resultado.saca();

            assertEquals(
                TipoDocumento.CONTRATO,
                documento.getTipo()
            );
        }
    }

    @Test
    public void obtenerPorTipoSinCoincidenciasDevuelvePilaVacia() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente = new Cliente("12345678");

        Documento documento = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente
        );

        copiaDocumentos.registrarDocumento(documento);

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorTipo(
                TipoDocumento.GARANTIA
            );

        assertTrue(resultado.esVacio());
    }

    @Test(expected = IllegalArgumentException.class)
    public void obtenerPorTipoNoPermiteTipoNulo() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        copiaDocumentos.obtenerPorTipo(null);
    }

    @Test
    public void filtrarNoEliminaDocumentosOriginales() {

        CopiaDocumentos copiaDocumentos = new CopiaDocumentos();

        Cliente cliente = new Cliente("12345678");

        Documento doc1 = new Documento(
            "DOC001",
            TipoDocumento.CONTRATO,
            cliente
        );

        Documento doc2 = new Documento(
            "DOC002",
            TipoDocumento.PAGARE,
            cliente
        );

        copiaDocumentos.registrarDocumento(doc1);
        copiaDocumentos.registrarDocumento(doc2);

        copiaDocumentos.obtenerPorTipo(
            TipoDocumento.CONTRATO
        );

        Pila<Documento> resultado =
            copiaDocumentos.obtenerPorCliente(cliente);

        assertEquals(2, resultado.tamano());
    }
}