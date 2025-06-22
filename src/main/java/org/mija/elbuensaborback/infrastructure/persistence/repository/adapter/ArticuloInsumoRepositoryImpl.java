package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.application.dto.response.ArticuloInsumoBasicResponse;
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
        return articuloInsumoJpaRepository.findById(aLong);
    }

    @Override
    public List<ArticuloInsumoEntity> findAll() {
        return articuloInsumoJpaRepository.findAll();
    }

    public List<ArticuloInsumoEntity> findAllVendibles() {
        return articuloInsumoJpaRepository.findAllAndEsVendibleTrueAndCategoria();
    }

    public List<ArticuloInsumoBasicResponse> basicFindAllParaPreparar() {
        return articuloInsumoJpaRepository.basicFindAllParaPreparar();
    }

    public List<ArticuloInsumoBasicResponse> basicFindAll() {
        return articuloInsumoJpaRepository.basicFindAll();
    }

    @Override
    public ArticuloInsumoEntity save(ArticuloInsumoEntity entity) {
        return articuloInsumoJpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {
        articuloInsumoJpaRepository.deleteById(aLong);
    }

    public List<ArticuloInsumoEntity> findAllById(List<Long> ids){
        return articuloInsumoJpaRepository.findAllById(ids);
    }

    public List<ArticuloInsumoEntity> findAllByCategoriaVendibles(String categoria){
        return articuloInsumoJpaRepository.findAllByCategoriaDenominacionVendibles(categoria);
    }

    @Override
    public List<ArticuloInsumoEntity> saveAll(List<ArticuloInsumoEntity> entities) {
        return List.of();
    }
}
