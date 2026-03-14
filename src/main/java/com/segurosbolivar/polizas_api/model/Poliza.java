package com.segurosbolivar.polizas_api.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poliza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo;   // INDIVIDUAL o COLECTIVA
    private String estado; // ACTIVA, RENOVADA, CANCELADA

    private LocalDate vigenciaInicio;
    private LocalDate vigenciaFin;

    private BigDecimal canonMensual;
    private BigDecimal prima;

    @OneToMany(mappedBy = "poliza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Riesgo> riesgos = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getVigenciaInicio() { return vigenciaInicio; }
    public void setVigenciaInicio(LocalDate vigenciaInicio) { this.vigenciaInicio = vigenciaInicio; }

    public LocalDate getVigenciaFin() { return vigenciaFin; }
    public void setVigenciaFin(LocalDate vigenciaFin) { this.vigenciaFin = vigenciaFin; }

    public BigDecimal getCanonMensual() { return canonMensual; }
    public void setCanonMensual(BigDecimal canonMensual) { this.canonMensual = canonMensual; }

    public BigDecimal getPrima() { return prima; }
    public void setPrima(BigDecimal prima) { this.prima = prima; }

    public List<Riesgo> getRiesgos() { return riesgos; }
    public void setRiesgos(List<Riesgo> riesgos) { this.riesgos = riesgos; }
}