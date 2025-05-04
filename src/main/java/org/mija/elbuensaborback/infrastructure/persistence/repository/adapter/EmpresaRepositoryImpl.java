package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.EmpresaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpresaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.EmpresaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaRepositoryImpl implements EmpresaRepositoryPort {
    private final EmpresaJpaRepository empresaJpaRepository;

    public EmpresaRepositoryImpl(EmpresaJpaRepository empresaJpaRepository) {
        this.empresaJpaRepository = empresaJpaRepository;
    }

    @Override
    public Optional<EmpresaEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<EmpresaEntity> findAll() {
        return empresaJpaRepository.findAll();
    }

    @Override
    public EmpresaEntity save(EmpresaEntity nombreEntidad) {
        return empresaJpaRepository.save(nombreEntidad);
    }

    @Override
    public void deleteById(Long id) {

    }
}
