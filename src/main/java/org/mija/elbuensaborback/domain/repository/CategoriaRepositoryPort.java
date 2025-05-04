package org.mija.elbuensaborback.domain.repository;


import org.mija.elbuensaborback.infrastructure.persistence.entity.CategoriaEntity;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepositoryPort{
    Optional<CategoriaEntity> findById(Long id);
    List<CategoriaEntity> findAll();
    CategoriaEntity save(CategoriaEntity nombreEntidad);
    void deleteById(Long id);
    List<CategoriaEntity> saveAll(List<CategoriaEntity> categoriaEntities);
}
