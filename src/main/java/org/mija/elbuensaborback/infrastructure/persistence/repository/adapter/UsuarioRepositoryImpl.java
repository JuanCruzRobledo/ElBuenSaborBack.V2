package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.UsuarioRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.UsuarioJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryPort {
    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @Override
    public Optional<UsuarioEntity> findById(Long aLong) {
        return Optional.empty();
    }

    public Optional<UsuarioEntity> findByEmail(String email) {
        return usuarioJpaRepository.findByEmail(email);
    }


    @Override
    public List<UsuarioEntity> findAll() {
        return List.of();
    }

    @Override
    public UsuarioEntity save(UsuarioEntity entity) {
        return usuarioJpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<UsuarioEntity> saveAll(List<UsuarioEntity> entities) {
        return usuarioJpaRepository.saveAll(entities);
    }
}
