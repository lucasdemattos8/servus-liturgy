package com.lsc.servus.liturgia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsc.servus.liturgia.entity.TemplateItem;

import java.util.List;
import java.util.UUID;

public interface TemplateItemRepository extends JpaRepository<TemplateItem, UUID> {

    List<TemplateItem> findByTemplateIdAndAtivoTrueOrderByOrdemAsc(UUID templateId);
}