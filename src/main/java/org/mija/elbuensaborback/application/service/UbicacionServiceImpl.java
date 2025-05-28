package org.mija.elbuensaborback.application.service;

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
public class UbicacionServiceImpl implements UbicacionService {

    private final LocalidadRepositoryImpl localidadRepository;
    private final PaisRepositoryImpl paisRepository;
    private final ProvinciaRepositoryImpl provinciaRepository;
    private final LocalidadMapper localidadMapper;
    private final PaisMapper paisMapper;
    private final ProvinciaMapper provinciaMapper;


    public UbicacionServiceImpl(LocalidadRepositoryImpl localidadRepository, PaisRepositoryImpl paisRepository, ProvinciaRepositoryImpl provinciaRepository, LocalidadMapper localidadMapper, PaisMapper paisMapper, ProvinciaMapper provinciaMapper) {
        this.localidadRepository = localidadRepository;
        this.paisRepository = paisRepository;
        this.provinciaRepository = provinciaRepository;
        this.localidadMapper = localidadMapper;
        this.paisMapper = paisMapper;
        this.provinciaMapper = provinciaMapper;
    }

    @Override
    public Set<LocalidadResponse> obtenerLocalidades() {
        return localidadRepository.findAll().stream().map(localidadMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<PaisResponse> obtenerPaises() {
        return paisRepository.findAll().stream().map(paisMapper::toResponse).collect(Collectors.toSet());
    }

    @Override
    public Set<ProvinciaResponse> obtenerProvincias() {
        return provinciaRepository.findAll().stream().map(provinciaMapper::toResponse).collect(Collectors.toSet());
    }
}
