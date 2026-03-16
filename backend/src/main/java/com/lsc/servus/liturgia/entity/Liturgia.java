package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "liturgias")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false, updatable = false)
    private LocalDateTime criadoEm;

    @OneToMany(mappedBy = "liturgia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LiturgiaItem> itens = new ArrayList<>();

    protected Liturgia(String nome, LocalDate data, LocalTime horarioInicio) {
        this.nome = nome;
        this.data = data;
        this.horarioInicio = horarioInicio;
        this.criadoEm = LocalDateTime.now();
    }

    public LiturgiaItem adicionarItem(
            String nome,
            Integer duracaoPrevistaMinutos,
            String descricao,
            String responsavel,
            String observacoes) {

        int novaOrdem = quantidadeItensAtivos() + 1;

        LiturgiaItem item = new LiturgiaItem(
                this,
                nome,
                novaOrdem,
                duracaoPrevistaMinutos,
                descricao,
                responsavel,
                observacoes);

        itens.add(item);
        return item;
    }

    public void moverItem(UUID itemId, Integer novaPosicao) {

        LiturgiaItem item = buscarItem(itemId);

        if (!item.estaAtivo()) {
            throw new IllegalStateException("Item cancelado não pode ser movido.");
        }

        List<LiturgiaItem> ativosOrdenados = itensAtivosOrdenados();

        if (novaPosicao < 1 || novaPosicao > ativosOrdenados.size()) {
            throw new IllegalArgumentException("Posição inválida.");
        }

        ativosOrdenados.remove(item);
        ativosOrdenados.add(novaPosicao - 1, item);

        reorganizar(ativosOrdenados);
    }

    public void cancelarItem(UUID itemId) {
        LiturgiaItem item = buscarItem(itemId);
        item.cancelar();
        reorganizar(itensAtivosOrdenados());
    }

    public void atualizarItem(UUID itemId,
            String nome,
            String descricao,
            Integer duracaoPrevistaMinutos,
            String responsavel,
            String observacoes) {

        LiturgiaItem item = buscarItem(itemId);
        item.atualizarDados(nome, descricao, duracaoPrevistaMinutos, responsavel, observacoes);
    }

    private void reorganizar(List<LiturgiaItem> ativosOrdenados) {
        for (int i = 0; i < ativosOrdenados.size(); i++) {
            ativosOrdenados.get(i).atualizarOrdem(i + 1);
        }
    }

    private int quantidadeItensAtivos() {
        return (int) itens.stream()
                .filter(LiturgiaItem::estaAtivo)
                .count();
    }

    private List<LiturgiaItem> itensAtivosOrdenados() {
        return itens.stream()
                .filter(LiturgiaItem::estaAtivo)
                .sorted(Comparator.comparing(LiturgiaItem::getOrdem))
                .toList();
    }

    private LiturgiaItem buscarItem(UUID itemId) {
        return itens.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));
    }
}