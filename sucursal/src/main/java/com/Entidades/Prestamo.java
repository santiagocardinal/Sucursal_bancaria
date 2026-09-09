package com.Entidades;

import com.example.Enums.EstadoProducto;
import com.example.Enums.Moneda;

 //Acá queda solo lo específico de un préstamo: monto, interés y cuotas.
public class Prestamo extends ProductoBase {

    private final double montoOriginal;
    private final double interes;
    private final int cuotasTotales;
    private int cuotasActual;

    public Prestamo(String id,double montoOriginal,double interes,int cuotasTotales, Moneda moneda) {

        super(id, moneda);

        if (montoOriginal <= 0) { //Si el monto que se pidió prestado es 0 o negativo, no tiene sentido seguir
            throw new IllegalArgumentException("El monto original debe ser mayor a 0");
        }

        if (interes < 0) {//El interés no puede ser 0 eso significaría que el banco te devuelve más plata de la que prestó, lo cual no tiene sentido
            throw new IllegalArgumentException("El interes no puede ser negativo");
        }

        if (cuotasTotales <= 0) {//Tiene que haber al menos 1 cuota para poder pagar el préstamo
            throw new IllegalArgumentException("Las cuotas totales deben ser mayores a 0");
        }

        this.montoOriginal = montoOriginal;
        this.interes = interes;
        this.cuotasTotales = cuotasTotales;

        this.cuotasActual = 0;
    }

    // Devuelve el monto originalmente pedido, sin intereses.
    public double getMontoOriginal() {
        return montoOriginal;
    }

    // Devuelve la tasa de interés aplicada al préstamo.
    public double getInteres() {
        return interes;
    }

    // Devuelve la cantidad total de cuotas pactadas.
    public int getCuotasTotales() {
        return cuotasTotales;
    }

    // Devuelve la cantidad de cuotas ya pagadas.
    public int getCuotasActual() {
        return cuotasActual;
    }

    // Calcula cuánto hay que pagar en la próxima cuota. Si ya se pagaron
    // todas las cuotas, no queda nada por pagar y devuelve 0.
    
    public double proximaCuota() {

        if (cuotasActual >= cuotasTotales) {
            return 0;
        }

        double montoConInteres =
            montoOriginal + (montoOriginal * interes);

        return montoConInteres / cuotasTotales;
    }

    // Registra el pago de una cuota.
    // Primero chequea que todavía quedencuotas por pagar (si no, IllegalStateException: no se puede pagar algo que ya está saldado).
    // Después compara el monto recibido contra lo que realmente corresponde pagar (proximaCuota()), permitiendo un
    // margen  de error de redondeo (0.01); si no coincide, tira IllegalArgumentException y no registra nada.
    // Si todo está bien, incrementa cuotasActual, y si con ese pago se llegó a la última cuota, el préstamo pasa automáticamente a CANCELADO
    // (usando el modificarEstado heredado de ProductoBase, en vez de tocar un campo propio).
    public void pagarCuota(double montoPagado)
    {

        if (cuotasActual >= cuotasTotales) {
            throw new IllegalStateException("El prestamo ya fue pagado completamente");
        }

        double cuotaEsperada = proximaCuota();

        if (Math.abs(montoPagado - cuotaEsperada) > 0.01) {
            throw new IllegalArgumentException("El monto pagado (" + montoPagado + ") no coincide con la cuota esperada (" + cuotaEsperada + ")"
            );
        }

        cuotasActual++;

        if (cuotasActual == cuotasTotales) {
            modificarEstado(EstadoProducto.CANCELADO);
        }
    }

    @Override
    public float getPrecioAPagar() {
        int cuotasRestantes = cuotasTotales - cuotasActual;
        return (float) (cuotasRestantes * proximaCuota());
    }
}