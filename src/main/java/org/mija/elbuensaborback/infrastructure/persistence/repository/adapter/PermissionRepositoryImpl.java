package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PermissionRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PermissionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PermissionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryPort {
    private final PermissionJpaRepository jpaRepository;

    public PermissionRepositoryImpl(PermissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<PermissionEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<PermissionEntity> findAll() {
        return List.of();
    }

    @Override
    public PermissionEntity save(PermissionEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<PermissionEntity> saveAll(List<PermissionEntity> entities) {
        return List.of();
    }
}
