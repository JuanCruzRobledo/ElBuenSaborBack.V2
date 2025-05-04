package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;

import java.util.List;
import java.util.Optional;

public interface LocalidadRepositoryPort {
    Optional<LocalidadEntity> findById(Long id);
    List<LocalidadEntity> findAll();
    LocalidadEntity save(LocalidadEntity nombreEntidad);
    void deleteById(Long id);
    List<LocalidadEntity> saveAll(List<LocalidadEntity> localidades);
}
