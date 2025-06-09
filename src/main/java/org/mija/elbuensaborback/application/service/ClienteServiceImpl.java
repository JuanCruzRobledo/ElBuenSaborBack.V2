package org.mija.elbuensaborback.application.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.cliente.ClienteUpdateRequest;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.mapper.ClienteMapper;
import org.mija.elbuensaborback.application.service.contratos.ClienteService;;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final PedidoServiceImpl pedidoService;
    private final ClienteRepositoryImpl clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteBasicResponse traerCliente(Long id) {
        ClienteEntity cliente = clienteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el cliente"));
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public ClienteBasicResponse actualizarCliente(Long id, ClienteUpdateRequest clienteUpdateRequest) {

        ClienteEntity cliente = clienteMapper.toEntity(clienteUpdateRequest);

        cliente.setId(id);

        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }
}
