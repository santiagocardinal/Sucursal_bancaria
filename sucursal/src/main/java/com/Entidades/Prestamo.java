package com.Entidades;

import java.util.Objects;

import com.example.Enums.EstadoProducto;

public class Prestamo implements IProducto {

    private final String id;
    private EstadoProducto estado;

    private final double montoOriginal;
    private final double interes;
    private final int cuotasTotales;
    private int cuotasActual;

   
    public Prestamo(String id,double montoOriginal,double interes,int cuotasTotales) {

        this.id = Objects.requireNonNull(id, "id");

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
        this.estado = EstadoProducto.ACTIVO;
    }


    @Override
    public String getId() {
        return id;
    }

    
    @Override
    public EstadoProducto getEstado() {
        return estado;
    }

    @Override
    public void modificarEstado(EstadoProducto nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado,"nuevoEstado"
        );
    }

    @Override
    public boolean estaVencido() {
        return estado == EstadoProducto.VENCIDO;
    }

    public double getMontoOriginal() {
        return montoOriginal;
    }

    public double getInteres() {
        return interes;
    }

    public int getCuotasTotales() {
        return cuotasTotales;
    }
    public int getCuotasActual() {
        return cuotasActual;
    }

    // Calcula cuánto hay que pagar en la próxima cuota. Si ya se pagaron
    // todas las cuotas, no queda nada por pagar y devuelve 0. 
    // Si no,reparte el monto original mas el interés en partes iguales entre
    // el total de cuotas (montoConInteres / cuotasTotales) — es decir,
    // todas las cuotas valen lo mismo, no se recalcula sobre el saldo
    // restante a medida que se van pagando.
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
    // Si todo está bien, incrementa cuotasActual, y si con ese pago se llegó a la última cuota, el préstamo pasa automáticamente a CANCELADO.
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
            estado = EstadoProducto.CANCELADO;
        }
    }
}