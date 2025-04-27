package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.FacturaMercadoPagoRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.FacturaMercadoPagoJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class FacturaMercadoPagoRepositoryImpl implements FacturaMercadoPagoRepositoryPort {
    private final FacturaMercadoPagoJpaRepository facturaMercadoPagoJpaRepository;

    public FacturaMercadoPagoRepositoryImpl(FacturaMercadoPagoJpaRepository facturaMercadoPagoJpaRepository) {
        this.facturaMercadoPagoJpaRepository = facturaMercadoPagoJpaRepository;
    }
}
