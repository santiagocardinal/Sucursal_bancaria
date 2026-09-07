package com.example.Caja_De_Herramientas.Arboles;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;
import org.junit.Test;
import com.example.Enums.NivelPrioridad;
import com.example.Caja_de_Herramientas.Arboles.*;

public class MonticuloPrioridadTest {

    // Estructura vacia
    @Test
    public void monticuloNuevoEstaVacio() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        assertTrue(monticulo.esVacio());
        assertEquals(0, monticulo.tamano());
    }

    @Test(expected = NoSuchElementException.class)
    public void frenteEnMonticuloVacioLanzaExcepcion() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();
        monticulo.frente();
    }

    @Test(expected = NoSuchElementException.class)
    public void quitaDeColaEnMonticuloVacioLanzaExcepcion() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();
        monticulo.quitaDeCola();
    }

    // Unico elemento
    @Test
    public void insertarUnSoloElemento() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        assertTrue(monticulo.poneEnCola("Cliente A", NivelPrioridad.NORMAL));
        assertFalse(monticulo.esVacio());
        assertEquals(1, monticulo.tamano());
        assertEquals("Cliente A", monticulo.frente());
    }

    @Test
    public void extraerUnicoElemento() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Cliente A", NivelPrioridad.NORMAL);
        String atendido = monticulo.quitaDeCola();

        assertEquals("Cliente A", atendido);
        assertTrue(monticulo.esVacio());
        assertEquals(0, monticulo.tamano());
    }

    // Orden por prioridad (Min-Heap)
    @Test
    public void extraerRespetaOrdenDePrioridad() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        // Se insertan en orden arbitrario
        monticulo.poneEnCola("Normal", NivelPrioridad.NORMAL);
        monticulo.poneEnCola("Urgente", NivelPrioridad.URGENTE);

        // El mas urgente (menor valor numerico) debe salir primero
        assertEquals("Urgente", monticulo.quitaDeCola());
        assertEquals("Normal", monticulo.quitaDeCola());
        assertTrue(monticulo.esVacio());
    }

    @Test
    public void insercionPorDefectoUsaPrioridadNormal() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Sin Prioridad");
        assertEquals(1, monticulo.tamano());
        assertEquals("Sin Prioridad", monticulo.frente());
    }

    // Modificar prioridad
    @Test
    public void aumentarUrgenciaReacomodaHaciaArriba() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Cliente 1", NivelPrioridad.NORMAL);
        monticulo.poneEnCola("Cliente 2", NivelPrioridad.NORMAL);

        // Cliente 2 pasa a ser URGENTE (debe subir a la raiz)
        boolean modificado = monticulo.modificarPrioridad("Cliente 2", NivelPrioridad.URGENTE);

        assertTrue(modificado);
        assertEquals("Cliente 2", monticulo.frente());
    }

    @Test
    public void disminuirUrgenciaReacomodaHaciaAbajo() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Cliente 1", NivelPrioridad.URGENTE);
        monticulo.poneEnCola("Cliente 2", NivelPrioridad.NORMAL);

        // Cliente 1 baja su urgencia a NORMAL
        boolean modificado = monticulo.modificarPrioridad("Cliente 1", NivelPrioridad.NORMAL);

        assertTrue(modificado);
        assertEquals(2, monticulo.tamano());
    }

    @Test
    public void modificarPrioridadClienteInexistente() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Cliente 1", NivelPrioridad.NORMAL);

        assertFalse(monticulo.modificarPrioridad("Cliente Fantasma", NivelPrioridad.URGENTE));
        assertFalse(monticulo.modificarPrioridad(null, NivelPrioridad.URGENTE));
    }

    // Vaciar
    @Test
    public void vaciarLimpiaTodosLosElementos() {
        MonticuloPrioridad<String> monticulo = new MonticuloPrioridad<>();

        monticulo.poneEnCola("Cliente 1", NivelPrioridad.NORMAL);
        monticulo.poneEnCola("Cliente 2", NivelPrioridad.URGENTE);

        monticulo.vaciar();

        assertTrue(monticulo.esVacio());
        assertEquals(0, monticulo.tamano());
    }
}