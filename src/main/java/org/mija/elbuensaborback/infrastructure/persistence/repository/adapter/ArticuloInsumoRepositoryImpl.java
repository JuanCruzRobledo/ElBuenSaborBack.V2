package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ArticuloRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Articulo;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ArticuloInsumoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticuloInsumoRepositoryImpl implements ArticuloRepositoryPort {
    private final ArticuloInsumoJpaRepository articuloInsumoJpaRepository;

    public ArticuloInsumoRepositoryImpl(ArticuloInsumoJpaRepository articuloInsumoJpaRepository) {
        this.articuloInsumoJpaRepository = articuloInsumoJpaRepository;
    }

    @Override
    public Optional<Articulo> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Articulo> findAll() {
        return List.of();
    }

    @Override
    public Articulo save(Articulo nombreEntidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
