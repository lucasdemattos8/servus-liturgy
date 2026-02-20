package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "liturgias")
@Getter
@Setter
public class Liturgia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horarioInicio;

    private UUID templateId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LiturgiaStatus status = LiturgiaStatus.PLANEJADA;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Liturgia() {
    }

    public Liturgia(String nome, LocalDate data, LocalTime horarioInicio, UUID templateId) {
        this.nome = nome;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.templateId = templateId;
        this.status = LiturgiaStatus.PLANEJADA;
        this.criadoEm = LocalDateTime.now();
    }

    public void iniciar() {
        if (this.status != LiturgiaStatus.PLANEJADA) {
            throw new IllegalStateException("Liturgia não pode ser iniciada");
        }
        this.status = LiturgiaStatus.EM_ANDAMENTO;
    }

    public void finalizar() {
        if (this.status != LiturgiaStatus.EM_ANDAMENTO) {
            throw new IllegalStateException("Liturgia não pode ser finalizada");
        }
        this.status = LiturgiaStatus.FINALIZADA;
    }

    public void cancelar() {
        this.status = LiturgiaStatus.CANCELADA;
    }
}