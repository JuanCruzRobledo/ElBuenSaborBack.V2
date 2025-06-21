package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioCreatedRequest;
import org.mija.elbuensaborback.application.dto.request.domicilio.DomicilioUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.DomicilioResponse;
import org.mija.elbuensaborback.application.mapper.DomicilioMapper;
import org.mija.elbuensaborback.application.mapper.LocalidadMapper;
import org.mija.elbuensaborback.application.service.contratos.DomicilioService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.LocalidadEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.DomicilioRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.LocalidadRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomicilioServiceImpl implements DomicilioService {

    private final ClienteRepositoryImpl clienteRepository;
    private final DomicilioRepositoryImpl domicilioRepository;
    private final LocalidadRepositoryImpl localidadRepository;
    private final DomicilioMapper domicilioMapper;
    private final LocalidadMapper localidadMapper;

    @Override
    @Transactional
    public DomicilioResponse crearDomicilio(Long idCliente, DomicilioCreatedRequest request) {
        ClienteEntity cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        LocalidadEntity localidad = localidadRepository.findById(request.localidadId())
                .orElseThrow(()->new EntityNotFoundException("Localidad no encontrada"));

        DomicilioEntity domicilioEntity = domicilioMapper.fromCreatedRequest(request);

        domicilioEntity.setLocalidad(localidad);
        domicilioEntity.setActivo(true);

        DomicilioEntity nuevoDomicilio = domicilioRepository.save(domicilioEntity);

        cliente.getDomicilio().add(nuevoDomicilio);
        clienteRepository.save(cliente);

        return domicilioMapper.toResponse(nuevoDomicilio);
    }

    @Override
    public DomicilioResponse actualizarDomicilio(Long idDomicilio, DomicilioUpdateRequest request) {
        DomicilioEntity domicilio = domicilioRepository.findById(idDomicilio)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado"));

        LocalidadEntity localidad = localidadRepository.findById(request.localidadId())
                .orElseThrow(()->new EntityNotFoundException("Localidad no encontrada"));

        domicilio.setCalle(request.calle());
        domicilio.setNumero(request.numero());
        domicilio.setDescripcion(request.descripcion());
        domicilio.setCodigoPostal(request.codigoPostal());
        domicilio.setLocalidad(localidad);

        domicilioRepository.save(domicilio);
        return domicilioMapper.toResponse(domicilio);
    }

    @Override
    public DomicilioResponse obtenerDomicilio(Long idDomicilio) {
        DomicilioEntity domicilio = domicilioRepository.findById(idDomicilio)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado"));

        if (!domicilio.isActivo()) {
            throw new ResourceNotFoundException("Domicilio no disponible");
        }

        return domicilioMapper.toResponse(domicilio);
    }

    @Override
    public void eliminarDomicilio(Long idDomicilio) {
        DomicilioEntity domicilio = domicilioRepository.findById(idDomicilio)
                .orElseThrow(() -> new RuntimeException("Domicilio no encontrado"));

        domicilio.setActivo(false);

        domicilioRepository.save(domicilio);
    }

    @Override
    public Set<DomicilioResponse> listarDomicilios(Long idCliente) {
        List<DomicilioEntity> domicilios = clienteRepository.findDomiciliosByClienteId(idCliente);
        return domicilios.stream()
                .map(domicilioMapper::toResponse)
                .collect(Collectors.toSet());
    }
}