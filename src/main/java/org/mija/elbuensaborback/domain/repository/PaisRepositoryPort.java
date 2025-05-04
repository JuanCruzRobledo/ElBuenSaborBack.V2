package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.PaisEntity;

import java.util.List;
import java.util.Optional;

public interface PaisRepositoryPort {
    Optional<PaisEntity> findById(Long id);
    List<PaisEntity> findAll();
    PaisEntity save(PaisEntity paisEntity);
    void deleteById(Long id);
    List<PaisEntity> saveAll(List<PaisEntity> paises);
}
