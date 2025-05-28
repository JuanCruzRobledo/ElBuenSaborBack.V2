package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {
    ClienteEntity findByUsuarioEmail(String email);
    @Query("SELECT c.domicilio FROM cliente c WHERE c.id = :idCliente")
    List<DomicilioEntity> findDomiciliosByClienteId(@Param("idCliente") Long idCliente);
    List<ClienteEntity> findAllByDomicilioContains(DomicilioEntity domicilio);
}
