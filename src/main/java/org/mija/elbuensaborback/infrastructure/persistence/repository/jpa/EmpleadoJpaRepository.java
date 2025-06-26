package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmpleadoJpaRepository extends JpaRepository<EmpleadoEntity, Long> {
    @Query("SELECT e FROM empleado e WHERE LOWER(e.usuario.email) = LOWER(:email)")
    EmpleadoEntity findByUsuarioEmail(@Param("email") String email);

}
