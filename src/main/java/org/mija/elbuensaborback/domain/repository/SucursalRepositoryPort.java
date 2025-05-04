package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;

import java.util.List;
import java.util.Optional;

public interface SucursalRepositoryPort {
    Optional<SucursalEntity> findById(Long id);
    List<SucursalEntity> findAll();
    SucursalEntity save(SucursalEntity nombreEntidad);
    void deleteById(Long id);
    List<SucursalEntity> saveAll(List<SucursalEntity> sucursales);
}
