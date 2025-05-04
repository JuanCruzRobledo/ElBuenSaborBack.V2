package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpresaEntity;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepositoryPort {
    Optional<EmpresaEntity> findById(Long id);
    List<EmpresaEntity> findAll();
    EmpresaEntity save(EmpresaEntity nombreEntidad);
    void deleteById(Long id);
}
