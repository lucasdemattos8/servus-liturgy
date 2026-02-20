package com.lsc.servus.liturgia.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLiturgiaItemRequest {

        private String nome;
        private Integer ordem;
        private Integer duracaoPrevistaMinutos;
        private String descricao;
        private String responsavel;
        private String observacoes;

}