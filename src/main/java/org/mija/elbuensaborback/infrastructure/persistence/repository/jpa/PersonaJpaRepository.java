package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonaJpaRepository extends JpaRepository<PersonaEntity, Long> {
    @Query("SELECT p FROM persona p WHERE LOWER(p.usuario.email) = LOWER(:email)")
    PersonaEntity findByUsuarioEmail(@Param("email") String email);
}
