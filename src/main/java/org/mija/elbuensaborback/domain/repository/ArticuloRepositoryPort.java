package org.mija.elbuensaborback.domain.repository;



import org.mija.elbuensaborback.infrastructure.persistence.entity.ArticuloEntity;

import java.util.List;
import java.util.Optional;

public interface ArticuloRepositoryPort{
    Optional<ArticuloEntity> findById(Long id);
    List<ArticuloEntity> findAll();
    ArticuloEntity save(ArticuloEntity nombreEntidad);
    void deleteById(Long id);
}
