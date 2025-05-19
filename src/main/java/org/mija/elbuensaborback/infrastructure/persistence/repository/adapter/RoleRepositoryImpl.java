package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.domain.repository.RoleRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepositoryPort {
    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Optional<RoleEntity> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<RoleEntity> findByRolEnum(RolEnum role) {
        return roleJpaRepository.findByRolEnum(role);
    }

    @Override
    public List<RoleEntity> findAll() {
        return List.of();
    }

    @Override
    public RoleEntity save(RoleEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<RoleEntity> saveAll(List<RoleEntity> entities) {
        return List.of();
    }
}
