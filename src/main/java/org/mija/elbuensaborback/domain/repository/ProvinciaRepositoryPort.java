package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ProvinciaEntity;

import java.util.List;
import java.util.Optional;

public interface ProvinciaRepositoryPort {
    Optional<ProvinciaEntity> findById(Long id);
    List<ProvinciaEntity> findAll();
    ProvinciaEntity save(ProvinciaEntity nombreEntidad);
    void deleteById(Long id);
    List<ProvinciaEntity> saveAll(List<ProvinciaEntity> provinciaEntities);
}
