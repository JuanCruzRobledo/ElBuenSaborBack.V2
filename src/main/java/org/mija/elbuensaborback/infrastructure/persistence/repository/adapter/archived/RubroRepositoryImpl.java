package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.archived;

import org.mija.elbuensaborback.domain.repository.archived.RubroRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.archived.Rubro;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.archived.RubroJpaRepository;

import java.util.List;
import java.util.Optional;

//@Repository
@Deprecated
public class RubroRepositoryImpl implements RubroRepositoryPort {
    private final RubroJpaRepository rubroJpaRepository;

    public RubroRepositoryImpl(RubroJpaRepository rubroJpaRepository) {
        this.rubroJpaRepository = rubroJpaRepository;
    }

    @Override
    public Optional<Rubro> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Rubro> findAll() {
        return List.of();
    }

    @Override
    public Rubro save(Rubro nombreEntidad) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<Rubro> saveAll(List<Rubro> rubros) {
        //return rubroJpaRepository.saveAll(rubros);
        return null;
    }
}
