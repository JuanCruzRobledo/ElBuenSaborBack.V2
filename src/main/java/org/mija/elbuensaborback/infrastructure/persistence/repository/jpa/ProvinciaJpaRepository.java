package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinciaJpaRepository extends JpaRepository<ProvinciaEntity, Long> {

    List<ProvinciaEntity> findAllByPaisId(Long id);
}
