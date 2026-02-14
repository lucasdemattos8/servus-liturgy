package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "template_items")
@Getter
@Setter
public class TemplateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private Template template;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Integer ordem;

    @Column(nullable = false)
    private Integer duracaoPrevistaMinutos;

    private String responsavelPadrao;

    @Column(columnDefinition = "TEXT")
    private String observacoesPadrao;

    @Column(nullable = false)
    private Boolean ativo = true;

    protected TemplateItem() {
    }

    public TemplateItem(
            Template template,
            String nome,
            Integer ordem,
            Integer duracaoPrevistaMinutos,
            String descricao,
            String responsavelPadrao,
            String observacoesPadrao) {
        this.template = template;
        this.nome = nome;
        this.ordem = ordem;
        this.duracaoPrevistaMinutos = duracaoPrevistaMinutos;
        this.descricao = descricao;
        this.responsavelPadrao = responsavelPadrao;
        this.observacoesPadrao = observacoesPadrao;
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    // getters
}