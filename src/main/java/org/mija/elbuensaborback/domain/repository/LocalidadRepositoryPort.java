package org.mija.elbuensaborback.domain.repository;

import org.mija.elbuensaborback.infrastructure.persistence.entity.Empresa;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Localidad;

import java.util.List;
import java.util.Optional;

public interface LocalidadRepositoryPort {
    Optional<Localidad> findById(Long id);
    List<Localidad> findAll();
    Localidad save(Localidad nombreEntidad);
    void deleteById(Long id);
    List<Localidad> saveAll(List<Localidad> localidades);
}
