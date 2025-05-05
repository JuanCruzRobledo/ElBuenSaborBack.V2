package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloInsumoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloInsumoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloInsumoRepositoryImpl implements ArticuloInsumoRepositoryPort {
    private final ArticuloInsumoJpaRepository articuloInsumoJpaRepository;

    public ArticuloInsumoRepositoryImpl(ArticuloInsumoJpaRepository articuloInsumoJpaRepository) {
        this.articuloInsumoJpaRepository = articuloInsumoJpaRepository;
    }


    @Override
    public Optional<ArticuloInsumoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<ArticuloInsumoEntity> findAll() {
        return List.of();
    }

    @Override
    public ArticuloInsumoEntity save(ArticuloInsumoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ArticuloInsumoEntity> saveAll(List<ArticuloInsumoEntity> entities) {
        return articuloInsumoJpaRepository.saveAll(entities);
    }
}
