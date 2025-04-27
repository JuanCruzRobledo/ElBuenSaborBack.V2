package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.PersonaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PersonaJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PersonaRepositoryImpl implements PersonaRepositoryPort {
    private final PersonaJpaRepository personaJpaRepository;

    public PersonaRepositoryImpl(PersonaJpaRepository personaJpaRepository) {
        this.personaJpaRepository = personaJpaRepository;
    }
}
