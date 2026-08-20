package com.Entidades;

import com.example.Enums.EstadoProducto;
import java.time.LocalDate;
import java.util.Objects;

public class TarjetaDeCredito implements IProducto {

    private final String id;
    private EstadoProducto estado;
    private final double limite;
    private double saldoDisponible;
    private final LocalDate fechaDeCierre;

    public TarjetaDeCredito(String id, double limite, LocalDate fechaDeCierre) {
        this.id = id;
        this.estado = EstadoProducto.ACTIVO;
        this.limite = limite;
        this.saldoDisponible = limite; 
        this.fechaDeCierre = Objects.requireNonNull(fechaDeCierre, "fechaDeCierre");//<-Esto me lo dio claude
    }

    public double montoDisponible() {
        return saldoDisponible;
    }

    public boolean retirarEfectivo(int monto) {
        if (monto <= 0 || monto > saldoDisponible) {
            return false;
        }
        this.saldoDisponible -= monto;
        return true;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EstadoProducto getEstado() {
        return estado;
    }

    //Lo mismo que en cuenta
    @Override
    public void modificarEstado(EstadoProducto nuevoEstado) {
        this.estado = Objects.requireNonNull(nuevoEstado, "nuevoEstado");
    }

    @Override
    public boolean estaVencido() {
        return estado == EstadoProducto.VENCIDO;
    }

    public double getLimite() {
        return limite;
    }

    public LocalDate getFechaDeCierre() {
        return fechaDeCierre;
    }
}
