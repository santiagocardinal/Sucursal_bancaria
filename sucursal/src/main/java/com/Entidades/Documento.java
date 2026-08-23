package com.Entidades;

import java.time.LocalDate;
import java.util.Objects;

import com.example.Enums.TipoDocumento;

public class Documento {

    private final String id;
    private final TipoDocumento tipo;
    private final Cliente cliente;
    private final LocalDate fechaPresentacion;
    private final LocalDate vigencia;

    public Documento(String id, TipoDocumento tipo, Cliente cliente, LocalDate fechaPresentacion, LocalDate vigencia) {
        this.id = Objects.requireNonNull(id, "El id no puede ser nulo");
        this.tipo = Objects.requireNonNull(tipo, "El tipo no puede ser nulo");
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo");
        this.fechaPresentacion = Objects.requireNonNull(fechaPresentacion, "La fecha de presentación no puede ser nula");
        this.vigencia = vigencia;
    }


    public String getId() {
        return id;
    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDate getFechaPresentacion() {
        return fechaPresentacion;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public boolean estaVigente() {
        if (vigencia == null) {
            return true; 
        }
        return !LocalDate.now().isAfter(vigencia);
    }
}