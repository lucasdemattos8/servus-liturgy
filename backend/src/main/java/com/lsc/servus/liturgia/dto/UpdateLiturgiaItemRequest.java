package com.lsc.servus.liturgia.dto;

public record UpdateLiturgiaItemRequest(
        String nome,
        Integer ordem,
        Integer duracaoPrevistaMinutos,
        String descricao,
        String responsavel,
        String observacoes) {
}