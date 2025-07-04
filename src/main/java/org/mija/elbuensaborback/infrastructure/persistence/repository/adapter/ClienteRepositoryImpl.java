package org.mija.elbuensaborback.infrastructure.persistence.repository.adapter;

import org.mija.elbuensaborback.domain.repository.ClienteRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.DomicilioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepositoryImpl implements ClienteRepositoryPort {
    private final ClienteJpaRepository clienteJpaRepository;

    public ClienteRepositoryImpl(ClienteJpaRepository clienteJpaRepository) {
        this.clienteJpaRepository = clienteJpaRepository;
    }

    @Override
    public Optional<ClienteEntity> findById(Long aLong) {
        return clienteJpaRepository.findById(aLong);
    }

    @Override
    public List<ClienteEntity> findAll() {
        return clienteJpaRepository.findAll();
    }

    @Override
    public ClienteEntity save(ClienteEntity entity) {
        return clienteJpaRepository.save(entity);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public List<ClienteEntity> saveAll(List<ClienteEntity> entities) {
        return List.of();
    }

    public ClienteEntity findByUsuarioEmail(String email) {
        return clienteJpaRepository.findByUsuarioEmail(email);
    }

    public List<DomicilioEntity> findDomiciliosByClienteId(Long clienteId) {
        return clienteJpaRepository.findDomiciliosActivosByClienteId(clienteId);
    }
    public List<ClienteEntity> findAllByDomicilioContains(DomicilioEntity domicilio){
      return clienteJpaRepository.findAllByDomicilioContains(domicilio);
    }
}
