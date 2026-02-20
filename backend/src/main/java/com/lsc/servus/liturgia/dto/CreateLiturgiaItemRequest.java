package com.lsc.servus.liturgia.dto;

public record CreateLiturgiaItemRequest(
        String nome,
        Integer ordem,
        Integer duracaoPrevistaMinutos,
        String descricao,
        String responsavel,
        String observacoes) {
}