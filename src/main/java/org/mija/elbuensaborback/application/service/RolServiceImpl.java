package org.mija.elbuensaborback.application.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.service.contratos.RolService;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.RoleRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl implements RolService {

    private final RoleRepositoryImpl roleRepository;

    @Override
    public List<RoleEntity> listarBasicRoles() {
        return roleRepository.findAll().stream()
                .filter(rol -> rol.getRolEnum() != RolEnum.CLIENTE)
                .toList();
    }
}
