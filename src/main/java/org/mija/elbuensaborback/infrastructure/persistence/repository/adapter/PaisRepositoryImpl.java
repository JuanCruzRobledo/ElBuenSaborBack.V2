package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PaisRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PaisEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PaisJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaisRepositoryImpl implements PaisRepositoryPort {
    private final PaisJpaRepository paisJpaRepository;

    public PaisRepositoryImpl(PaisJpaRepository paisJpaRepository) {
        this.paisJpaRepository = paisJpaRepository;
    }

    @Override
    public Optional<PaisEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<PaisEntity> findAll() {
        return paisJpaRepository.findAll();
    }

    @Override
    public PaisEntity save(PaisEntity paisEntity) {
        return paisJpaRepository.save(paisEntity);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<PaisEntity> saveAll(List<PaisEntity> paises) {
        return List.of();
    }
}
