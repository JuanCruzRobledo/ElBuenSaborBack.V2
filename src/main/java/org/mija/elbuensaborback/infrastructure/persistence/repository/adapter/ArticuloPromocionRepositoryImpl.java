package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloPromocionRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloPromocionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloPromocionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloPromocionRepositoryImpl implements ArticuloPromocionRepositoryPort {
    private final ArticuloPromocionJpaRepository articuloPromocionJpaRepository;

    public ArticuloPromocionRepositoryImpl(ArticuloPromocionJpaRepository articuloPromocionJpaRepository) {
        this.articuloPromocionJpaRepository = articuloPromocionJpaRepository;
    }

    @Override
    public Optional<ArticuloPromocionEntity> findById(Long aLong) {
        return articuloPromocionJpaRepository.findById(aLong);
    }

    @Override
    public List<ArticuloPromocionEntity> findAll() {
        return articuloPromocionJpaRepository.findAll();
    }

    @Override
    public ArticuloPromocionEntity save(ArticuloPromocionEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ArticuloPromocionEntity> saveAll(List<ArticuloPromocionEntity> entities) {
        return List.of();
    }
}
