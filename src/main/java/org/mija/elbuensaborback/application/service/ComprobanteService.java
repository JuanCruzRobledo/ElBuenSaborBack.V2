package org.mija.elbuensaborback.application.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ComprobanteCounter;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ComprobanteCounterJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ComprobanteService {

    private final ComprobanteCounterJpaRepository counterRepository;


    @Transactional
    public Long generarNumeroComprobante(String tipo) {
        ComprobanteCounter counter = counterRepository.findById(tipo)
                .orElseThrow(() -> new RuntimeException("No existe contador para el tipo: " + tipo));

        Long nuevoNumero = counter.getUltimoNumero() + 1;
        counter.setUltimoNumero(nuevoNumero);
        counterRepository.save(counter);
        return nuevoNumero;
    }
}
