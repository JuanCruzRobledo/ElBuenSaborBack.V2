package org.mija.elbuensaborback.application.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.response.LocalidadResponse;
import org.mija.elbuensaborback.application.dto.response.PaisResponse;
import org.mija.elbuensaborback.application.dto.response.ProvinciaResponse;
import org.mija.elbuensaborback.application.mapper.LocalidadMapper;
import org.mija.elbuensaborback.application.mapper.PaisMapper;
import org.mija.elbuensaborback.application.mapper.ProvinciaMapper;
import org.mija.elbuensaborback.application.service.contratos.UbicacionService;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.LocalidadRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PaisRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ProvinciaRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UbicacionServiceImpl implements UbicacionService {

    private final LocalidadRepositoryImpl localidadRepository;
    private final PaisRepositoryImpl paisRepository;
    private final ProvinciaRepositoryImpl provinciaRepository;
    private final LocalidadMapper localidadMapper;
    private final PaisMapper paisMapper;
    private final ProvinciaMapper provinciaMapper;

    @Override
    public Set<LocalidadResponse> obtenerLocalidades(Long id) {
        return localidadRepository.findAllByProvinciaId(id).stream().map(localidadMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<PaisResponse> obtenerPaises() {
        return paisRepository.findAll().stream().map(paisMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<ProvinciaResponse> obtenerProvincias(Long id) {
        return provinciaRepository.findAllByPaisId(id).stream().map(provinciaMapper::toResponse).collect(Collectors.toSet());
    }
}
