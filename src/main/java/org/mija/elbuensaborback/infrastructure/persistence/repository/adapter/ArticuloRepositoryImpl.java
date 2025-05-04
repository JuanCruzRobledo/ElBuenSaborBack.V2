package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloRepositoryImpl implements ArticuloRepositoryPort {
    private final ArticuloJpaRepository articuloJpaRepository;

    public ArticuloRepositoryImpl(ArticuloJpaRepository articuloJpaRepository) {
        this.articuloJpaRepository = articuloJpaRepository;
    }

    @Override
    public Optional<ArticuloEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ArticuloEntity> findAll() {
        return List.of();
    }

    @Override
    public ArticuloEntity save(ArticuloEntity nombreEntidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
    }
}
