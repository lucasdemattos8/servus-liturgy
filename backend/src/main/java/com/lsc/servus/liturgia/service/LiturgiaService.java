package com.lsc.servus.liturgia.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.lsc.servus.liturgia.dto.CreateLiturgiaItemRequest;
import com.lsc.servus.liturgia.dto.UpdateLiturgiaItemRequest;
import com.lsc.servus.liturgia.dto.UpdateLiturgiaRequest;
import com.lsc.servus.liturgia.entity.Liturgia;
import com.lsc.servus.liturgia.entity.LiturgiaItem;
import com.lsc.servus.liturgia.entity.LiturgiaStatus;
import com.lsc.servus.liturgia.repository.LiturgiaItemRepository;
import com.lsc.servus.liturgia.repository.LiturgiaRepository;

import jakarta.transaction.Transactional;

@Service
public class LiturgiaService {

    private final LiturgiaRepository liturgiaRepository;
    private final LiturgiaItemRepository liturgiaItemRepository;

    public LiturgiaService(
            LiturgiaRepository liturgiaRepository,
            LiturgiaItemRepository liturgiaItemRepository) {
        this.liturgiaRepository = liturgiaRepository;
        this.liturgiaItemRepository = liturgiaItemRepository;
    }

    public Liturgia buscarPorId(UUID id) {
        return liturgiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Liturgia não encontrada"));
    }

    public List<LiturgiaItem> listarItens(UUID liturgiaId) {

        if (!liturgiaRepository.existsById(liturgiaId)) {
            throw new RuntimeException("Liturgia não encontrada");
        }

        return liturgiaItemRepository
                .findByLiturgiaIdAndAtivoTrueOrderByOrdemAsc(liturgiaId);
    }

    @Transactional
    public Liturgia atualizar(UUID id, UpdateLiturgiaRequest request) {

        Liturgia liturgia = buscarPorId(id);

        if (liturgia.getStatus() == LiturgiaStatus.FINALIZADA ||
                liturgia.getStatus() == LiturgiaStatus.CANCELADA) {
            throw new RuntimeException("Não é possível editar essa liturgia");
        }

        liturgia.setNome(request.nome());
        liturgia.setData(request.data());
        liturgia.setHorarioInicio(request.horarioInicio());

        return liturgia;
    }

    @Transactional
    public void alterarStatus(UUID id, LiturgiaStatus status) {

        Liturgia liturgia = buscarPorId(id);

        switch (status) {
            case EM_ANDAMENTO -> liturgia.iniciar();
            case FINALIZADA -> liturgia.finalizar();
            case CANCELADA -> liturgia.cancelar();
            default -> throw new RuntimeException("Transição inválida");
        }
    }

    @Transactional
    public LiturgiaItem adicionarItem(
            UUID liturgiaId,
            CreateLiturgiaItemRequest request) {

        Liturgia liturgia = buscarPorId(liturgiaId);

        if (liturgia.getStatus() == LiturgiaStatus.FINALIZADA || liturgia.getStatus() == LiturgiaStatus.CANCELADA) {
            throw new RuntimeException("Não é possível adicionar item");
        }

        LiturgiaItem item = new LiturgiaItem(
                liturgia,
                request.nome(),
                request.ordem(),
                request.duracaoPrevistaMinutos(),
                request.descricao(),
                request.responsavel(),
                request.observacoes());

        return liturgiaItemRepository.save(item);
    }

    @Transactional
    public LiturgiaItem atualizarItem(
            UUID itemId,
            UpdateLiturgiaItemRequest request) {

        LiturgiaItem item = liturgiaItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Liturgia liturgia = item.getLiturgia();

        if (liturgia.getStatus() == LiturgiaStatus.FINALIZADA ||
                liturgia.getStatus() == LiturgiaStatus.CANCELADA) {
            throw new RuntimeException("Não é possível editar item");
        }

        item.setNome(request.nome());
        item.setOrdem(request.ordem());
        item.setDuracaoPrevistaMinutos(request.duracaoPrevistaMinutos());
        item.setDescricao(request.descricao());
        item.setResponsavel(request.responsavel());
        item.setObservacoes(request.observacoes());

        return item;
    }

    @Transactional
    public void desativarItem(UUID itemId) {

        LiturgiaItem item = liturgiaItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Liturgia liturgia = item.getLiturgia();

        if (liturgia.getStatus() == LiturgiaStatus.FINALIZADA) {
            throw new RuntimeException("Não é possível alterar item de liturgia finalizada");
        }

        item.desativar();
    }
}