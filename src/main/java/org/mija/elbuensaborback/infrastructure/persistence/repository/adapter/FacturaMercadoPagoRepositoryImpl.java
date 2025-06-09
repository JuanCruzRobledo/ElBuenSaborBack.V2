package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.FacturaMercadoPagoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DatosMercadoPagoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.FacturaMercadoPagoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FacturaMercadoPagoRepositoryImpl implements FacturaMercadoPagoRepositoryPort {
    private final FacturaMercadoPagoJpaRepository facturaMercadoPagoJpaRepository;

    public FacturaMercadoPagoRepositoryImpl(FacturaMercadoPagoJpaRepository facturaMercadoPagoJpaRepository) {
        this.facturaMercadoPagoJpaRepository = facturaMercadoPagoJpaRepository;
    }

    @Override
    public Optional<DatosMercadoPagoEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<DatosMercadoPagoEntity> findAll() {
        return List.of();
    }

    @Override
    public DatosMercadoPagoEntity save(DatosMercadoPagoEntity entity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<DatosMercadoPagoEntity> saveAll(List<DatosMercadoPagoEntity> entities) {
        return List.of();
    }
}
