package org.mija.elbuensaborback.domain.repository;


import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloInsumoEntity;


import java.util.List;
import java.util.Optional;

public interface ArticuloInsumoRepositoryPort {
    Optional<ArticuloInsumoEntity> findById(Long id);
    List<ArticuloInsumoEntity> findAll();
    ArticuloInsumoEntity save(ArticuloInsumoEntity nombreEntidad);
    void deleteById(Long id);
    List<ArticuloInsumoEntity> saveAll(List<ArticuloInsumoEntity> insumos);
}
