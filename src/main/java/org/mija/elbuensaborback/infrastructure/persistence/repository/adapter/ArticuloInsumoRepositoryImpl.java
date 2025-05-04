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
    public Optional<ArticuloInsumoEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ArticuloInsumoEntity> findAll() {
        return articuloInsumoJpaRepository.findAll();
    }

    @Override
    public ArticuloInsumoEntity save(ArticuloInsumoEntity nombreEntidad) {
        return articuloInsumoJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<ArticuloInsumoEntity> saveAll(List<ArticuloInsumoEntity> insumos) {
        return articuloInsumoJpaRepository.saveAll(insumos);
    }
}
