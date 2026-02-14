package com.lsc.servus.liturgia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsc.servus.liturgia.entity.LiturgiaItem;

import java.util.List;
import java.util.UUID;

public interface LiturgiaItemRepository extends JpaRepository<LiturgiaItem, UUID> {

    List<LiturgiaItem> findByLiturgiaIdAndAtivoTrueOrderByOrdemAsc(UUID liturgiaId);
}