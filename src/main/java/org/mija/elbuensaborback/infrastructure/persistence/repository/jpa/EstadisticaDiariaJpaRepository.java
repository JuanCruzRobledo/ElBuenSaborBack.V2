package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.EstadisticaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstadisticaDiariaJpaRepository extends JpaRepository<EstadisticaDiaria, LocalDate> {
    List<EstadisticaDiaria> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    Optional<EstadisticaDiaria> findByFecha(LocalDate fecha);
}
