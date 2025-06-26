package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PersonaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PersonaEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PersonaJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonaRepositoryImpl implements PersonaRepositoryPort {
    private final PersonaJpaRepository personaJpaRepository;

    public PersonaRepositoryImpl(PersonaJpaRepository personaJpaRepository) {
        this.personaJpaRepository = personaJpaRepository;
    }

    @Override
    public Optional<PersonaEntity> findById(Long aLong) {
        return personaJpaRepository.findById(aLong);
    }

    @Override
    public List<PersonaEntity> findAll() {
        return List.of();
    }

    @Override
    public PersonaEntity save(PersonaEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    public PersonaEntity findByUsuarioEmail(String email) {
        return personaJpaRepository.findByUsuarioEmail(email);
    }

    @Override
    public List<PersonaEntity> saveAll(List<PersonaEntity> entities) {
        return personaJpaRepository.saveAll(entities);
    }
}
