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

    public Prestamo(
        String id,
        double montoOriginal,
        double interes,
        int cuotasTotales
    ) {

        this.id = Objects.requireNonNull(id, "id");

        if (montoOriginal <= 0) {
            throw new IllegalArgumentException(
                "El monto original debe ser mayor a 0"
            );
        }

        if (interes < 0) {
            throw new IllegalArgumentException(
                "El interes no puede ser negativo"
            );
        }

        if (cuotasTotales <= 0) {
            throw new IllegalArgumentException(
                "Las cuotas totales deben ser mayores a 0"
            );
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
        this.estado = Objects.requireNonNull(
            nuevoEstado,
            "nuevoEstado"
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

    public double proximaCuota() {

        if (cuotasActual >= cuotasTotales) {
            return 0;
        }

        double montoConInteres =
            montoOriginal + (montoOriginal * interes);

        return montoConInteres / cuotasTotales;
    }

    public void pagarCuota(double montoPagado) 
    {

        if (cuotasActual >= cuotasTotales) {
            throw new IllegalStateException("El prestamo ya fue pagado completamente");
        }

        double cuotaEsperada = proximaCuota();

        if (Math.abs(montoPagado - cuotaEsperada) > 0.01) {
            throw new IllegalArgumentException(
                "El monto pagado (" + montoPagado + ") no coincide con la cuota esperada (" + cuotaEsperada + ")"
            );
        }

        cuotasActual++;

        if (cuotasActual == cuotasTotales) {
            estado = EstadoProducto.CANCELADO;
        }
    }
}