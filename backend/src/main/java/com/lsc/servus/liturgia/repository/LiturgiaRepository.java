package com.lsc.servus.liturgia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lsc.servus.liturgia.entity.Liturgia;

import java.util.UUID;

public interface LiturgiaRepository extends JpaRepository<Liturgia, UUID> {
}