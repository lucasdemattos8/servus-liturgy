package com.lsc.servus.liturgia.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "templates")
@Getter
@Setter
public class Template {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false)
    private LocalDateTime criadoEm = LocalDateTime.now();

    public Template() {
    }

    public Template(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
        this.ativo = true;
        this.criadoEm = LocalDateTime.now();
    }

    public void desativar() {
        this.ativo = false;
    }
}