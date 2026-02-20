package com.lsc.servus.liturgia.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateLiturgiaRequest(
        String nome,
        LocalDate data,
        LocalTime horarioInicio,
        UUID templateId) {
}