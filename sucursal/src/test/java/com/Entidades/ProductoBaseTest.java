package com.Entidades;

import static org.junit.Assert.*;

import org.junit.Test;
import com.example.Enums.Moneda;

import com.example.Caja_de_Herramientas.Lista.ListaEnlazada;


public class ProductoBaseTest {

    // Un producto recién creado, sin que se le haya agregado ningún
    // componente, tiene que comportarse como una hoja.
    @Test
    public void testProductoNuevoEsHojaSinComponentes() {
        Cuenta cuenta = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);

        assertTrue(cuenta.esHoja());
        assertTrue(cuenta.obtenerComponentes().esVacio());
    }

    // Al agregar un componente, este queda accesible desde obtenerComponentes()
    // y el producto deja de ser hoja.
    @Test
    public void testAgregarComponenteQuedaEnObtenerComponentesYDejaDeSerHoja() {
        Cuenta cuentaMadre = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);
        Cuenta subcuentaUSD = new Cuenta("CTA-1-USD", 200, Moneda.USD);

        cuentaMadre.agregarComponente(subcuentaUSD);

        assertFalse(cuentaMadre.esHoja());
        assertEquals(1, cuentaMadre.obtenerComponentes().tamano());
        assertSame(subcuentaUSD, cuentaMadre.obtenerComponentes().obtener(0));
    }

    // Se pueden agregar varios componentes directos al mismo producto, y
    // pueden ser de distinto tipo concreto 
    @Test
    public void testSePuedenAgregarVariosComponentesDeDistintoTipo() {
        Cuenta cuentaMadre = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);
        Cuenta subcuentaEUR = new Cuenta("CTA-1-EUR", 50, Moneda.PESO_URUGUAYO);
        TarjetaDeCredito adicional = new TarjetaDeCredito("TC-ADIC-1", Moneda.PESO_URUGUAYO, 1000f);

        cuentaMadre.agregarComponente(subcuentaEUR);
        cuentaMadre.agregarComponente(adicional);

        assertEquals(2, cuentaMadre.obtenerComponentes().tamano());
        assertTrue(cuentaMadre.obtenerComponentes().contiene(subcuentaEUR));
        assertTrue(cuentaMadre.obtenerComponentes().contiene(adicional));
    }

    // agregarComponente(null) no tiene sentido (no se puede colgar "nada"
    // como hijo), así que tiene que fallar en vez de agregar un hueco.
    @Test(expected = IllegalArgumentException.class)
    public void testAgregarComponenteNuloLanzaExcepcion() {
        Cuenta cuenta = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);

        cuenta.agregarComponente(null);
    }

    // Mejor caso de recorrer(): un producto sin componentes se visita
    // únicamente a sí mismo.
    @Test
    public void testRecorrerSinComponentesVisitaSoloAEsteProducto() {
        Cuenta cuenta = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);

        ListaEnlazada<IProducto> visitados = new ListaEnlazada<>();
        cuenta.recorrer(visitados::agregar);

        assertEquals(1, visitados.tamano());
        assertSame(cuenta, visitados.obtener(0));
    }

    // recorrer() tiene que bajar por todos los niveles de composición, sin
    // límite de profundidad: acá se arma cuentaMadre -> subUSD -> adicional
    // (tres niveles) y se verifica que los tres se visiten.
    @Test
    public void testRecorrerVisitaComponentesAnidadosSinLimiteDeProfundidad() {
        Cuenta cuentaMadre = new Cuenta("CTA-1", 1000, Moneda.PESO_URUGUAYO);
        Cuenta subUSD = new Cuenta("CTA-1-USD", 200, Moneda.USD);
        TarjetaDeCredito adicional = new TarjetaDeCredito("TC-ADIC-1", Moneda.PESO_URUGUAYO, 1000f);

        cuentaMadre.agregarComponente(subUSD);
        subUSD.agregarComponente(adicional); // segundo nivel de profundidad

        ListaEnlazada<IProducto> visitados = new ListaEnlazada<>();
        cuentaMadre.recorrer(visitados::agregar);

        assertEquals(3, visitados.tamano());
        assertTrue(visitados.contiene(cuentaMadre));
        assertTrue(visitados.contiene(subUSD));
        assertTrue(visitados.contiene(adicional));
    }

    
}