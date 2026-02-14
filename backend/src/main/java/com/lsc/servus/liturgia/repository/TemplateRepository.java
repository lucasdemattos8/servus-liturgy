package com.lsc.servus.liturgia.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsc.servus.liturgia.entity.Template;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
}
