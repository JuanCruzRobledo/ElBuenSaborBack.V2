package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PermissionRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PermissionJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PermissionRepositoryImpl implements PermissionRepositoryPort {
    private final PermissionJpaRepository jpaRepository;

    public PermissionRepositoryImpl(PermissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
}
