package org.mija.elbuensaborback.application.service;

import org.mija.elbuensaborback.domain.repository.EmpresaRepositoryPort;
import org.mija.elbuensaborback.infrastructure.persistence.entity.Empresa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final EmpresaRepositoryPort empresaRepository;

    public AdminService(EmpresaRepositoryPort empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> findAllEmpresas() {
        return empresaRepository.findAll();
    }
}
