package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "liturgia_items")
@Getter
@Setter
public class LiturgiaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "liturgia_id", nullable = false)
    private Liturgia liturgia;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Integer duracaoPrevistaMinutos;

    private String responsavel;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false)
    private Boolean ativo = true;

    public LiturgiaItem() {
    }

    public LiturgiaItem(
            Liturgia liturgia,
            String nome,
            Integer ordem,
            Integer duracaoPrevistaMinutos,
            String descricao,
            String responsavel,
            String observacoes) {
        this.liturgia = liturgia;
        this.nome = nome;
        this.ordem = ordem;
        this.duracaoPrevistaMinutos = duracaoPrevistaMinutos;
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }
}