package com.lsc.servus.liturgia.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLiturgiaRequest {

    private String nome;
    private LocalDate data;
    private LocalTime horarioInicio;
    private UUID templateId;

}