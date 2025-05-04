package org.mija.elbuensaborback.domain.repository.archived;

import org.mija.elbuensaborback.infrastructure.persistence.entity.archived.SubRubro;

import java.util.List;
import java.util.Optional;

public interface SubRubroRepositoryPort {
    Optional<SubRubro> findById(Long id);
    List<SubRubro> findAll();
    SubRubro save(SubRubro nombreEntidad);
    void deleteById(Long id);
    List<SubRubro> saveAll(List<SubRubro> subRubros);
}
