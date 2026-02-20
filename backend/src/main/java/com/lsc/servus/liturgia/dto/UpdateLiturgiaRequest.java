package com.lsc.servus.liturgia.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateLiturgiaRequest(
        String nome,
        LocalDate data,
        LocalTime horarioInicio) {
}
