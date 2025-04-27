package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Empresa;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepositoryPort {
    Optional<Empresa> findById(Long id);
    List<Empresa> findAll();
    Empresa save(Empresa nombreEntidad);
    void deleteById(Long id);
}
