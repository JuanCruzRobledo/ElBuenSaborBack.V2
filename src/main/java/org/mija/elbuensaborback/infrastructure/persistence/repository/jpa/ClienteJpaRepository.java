package org.mija.elbuensaborback.infrastructure.persistence.repository.jpa;

import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, Long> {

    @Query("SELECT c FROM cliente c WHERE LOWER(c.usuario.email) = LOWER(:email)")
    ClienteEntity findByUsuarioEmail(@Param("email") String email);

    @Query("SELECT d FROM cliente c JOIN c.domicilio d WHERE c.id = :idCliente AND d.activo = true")
    List<DomicilioEntity> findDomiciliosActivosByClienteId(@Param("idCliente") Long idCliente);


    List<ClienteEntity> findAllByDomicilioContains(DomicilioEntity domicilio);
}
