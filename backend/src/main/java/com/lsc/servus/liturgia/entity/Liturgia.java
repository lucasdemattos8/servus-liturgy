package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "liturgias")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Liturgia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "liturgia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LiturgiaItem> itens = new ArrayList<>();

    public LiturgiaItem adicionarItem(
            String nome,
            Integer duracaoPrevistaMinutos,
            String descricao,
            String responsavel,
            String observacoes) {

        Integer novaOrdem = itens.size() + 1;

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

        List<LiturgiaItem> ativos = itens.stream()
                .filter(LiturgiaItem::estaAtivo)
                .sorted(Comparator.comparing(LiturgiaItem::getOrdem))
                .collect(Collectors.toCollection(ArrayList::new));

        LiturgiaItem item = buscarItem(itemId);

        ativos.remove(item);
        ativos.add(novaPosicao - 1, item);

        for (int i = 0; i < ativos.size(); i++) {
            ativos.get(i).atualizarOrdem(i + 1);
        }
    }

    private LiturgiaItem buscarItem(UUID itemId) {
        return itens.stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
}