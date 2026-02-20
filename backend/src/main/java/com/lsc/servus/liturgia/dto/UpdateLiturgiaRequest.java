package com.lsc.servus.liturgia.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateLiturgiaRequest {

        public String nome;
        public LocalDate data;
        public LocalTime horarioInicio;
}
