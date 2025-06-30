package org.mija.elbuensaborback.application.service.contratos;

import org.mija.elbuensaborback.application.dto.response.RolResponse;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;

import java.util.List;

public interface RolService {
    List<RolResponse> listarBasicRoles();
}
