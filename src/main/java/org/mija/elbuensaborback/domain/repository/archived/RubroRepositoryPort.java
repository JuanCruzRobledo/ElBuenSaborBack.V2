package org.mija.elbuensaborback.domain.repository.archived;

import org.mija.elbuensaborback.infrastructure.persistence.entity.archived.Rubro;

import java.util.List;
import java.util.Optional;

public interface RubroRepositoryPort {

    Optional<Rubro> findById(Long id);
    List<Rubro> findAll();
    Rubro save(Rubro nombreEntidad);
    void deleteById(Long id);
    List<Rubro> saveAll(List<Rubro> rubros);
}
