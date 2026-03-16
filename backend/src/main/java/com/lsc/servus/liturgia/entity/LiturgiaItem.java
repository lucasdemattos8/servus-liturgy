package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "liturgia_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LiturgiaItemStatus status;

    protected LiturgiaItem(
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
        this.status = LiturgiaItemStatus.PLANEJADO;
    }

    protected void atualizarOrdem(Integer novaOrdem) {
        this.ordem = novaOrdem;
    }

    protected void atualizarDados(
            String nome,
            String descricao,
            Integer duracaoPrevistaMinutos,
            String responsavel,
            String observacoes) {
        this.nome = nome;
        this.descricao = descricao;
        this.duracaoPrevistaMinutos = duracaoPrevistaMinutos;
        this.responsavel = responsavel;
        this.observacoes = observacoes;
    }

    protected void cancelar() {
        if (this.status == LiturgiaItemStatus.CANCELADO) {
            return;
        }
        this.status = LiturgiaItemStatus.CANCELADO;
    }

    protected void iniciar() {
        if (this.status == LiturgiaItemStatus.CANCELADO) {
            throw new IllegalStateException("Item cancelado não pode iniciar.");
        }
        this.status = LiturgiaItemStatus.EM_PROGRESSO;
    }

    protected void concluir() {
        if (this.status != LiturgiaItemStatus.EM_PROGRESSO) {
            throw new IllegalStateException("Só pode concluir item em progresso.");
        }
        this.status = LiturgiaItemStatus.CONCLUIDO;
    }

    public boolean estaAtivo() {
        return status != LiturgiaItemStatus.CANCELADO;
    }
}