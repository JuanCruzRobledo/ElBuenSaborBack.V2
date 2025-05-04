package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.archived;

import org.mija.elbuensaborback.domain.repository.archived.SubRubroRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.archived.SubRubro;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.archived.SubRubroJpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
@Deprecated
public class SubRubroRepositoryImpl implements SubRubroRepositoryPort {
    private final SubRubroJpaRepository subRubroJpaRepository;

    public SubRubroRepositoryImpl(SubRubroJpaRepository subRubroJpaRepository) {
        this.subRubroJpaRepository = subRubroJpaRepository;
    }

    @Override
    public Optional<SubRubro> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<SubRubro> findAll() {
        return List.of();
    }

    @Override
    public SubRubro save(SubRubro nombreEntidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<SubRubro> saveAll(List<SubRubro> subRubros) {
        //return subRubroJpaRepository.saveAll(subRubros);
        return null;
    }
}
