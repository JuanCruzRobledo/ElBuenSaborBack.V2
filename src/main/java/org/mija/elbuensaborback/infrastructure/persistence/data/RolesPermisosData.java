package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.PermissionEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.PermissionEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.PermissionJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RolesPermisosData {

    private final RoleJpaRepository roleRepository;
    private final PermissionJpaRepository permissionRepository;

    public void initRoleAndPermission(){
        /* CREATE PERMISSIONS */
        PermissionEntity permissionEntity1 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.CREATE)
                .build();

        PermissionEntity permissionEntity2 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.DELETE)
                .build();

        PermissionEntity permissionEntity3 = PermissionEntity.builder()
                .permissionEnum(PermissionEnum.UPDATE)
                .build();

        permissionRepository.saveAll(List.of(permissionEntity1, permissionEntity2, permissionEntity3));

        /* CREATE ROLES */
        RoleEntity roleAdmin = RoleEntity.builder()
                .rolEnum(RolEnum.ADMIN)
                .permisos(new HashSet<>(Set.of(permissionEntity1, permissionEntity2, permissionEntity3))) // Usa new HashSet<>()
                .build();

        RoleEntity roleCliente = RoleEntity.builder()
                .rolEnum(RolEnum.CLIENTE)
                .permisos(new HashSet<>(Set.of(permissionEntity1, permissionEntity2))) // Usa new HashSet<>()
                .build();

        RoleEntity roleEmpleado = RoleEntity.builder()
                .rolEnum(RolEnum.EMPLEADO)
                .permisos(new HashSet<>(Set.of(permissionEntity1))) // Usa new HashSet<>()
                .build();

        roleRepository.saveAll(List.of(roleAdmin, roleCliente, roleEmpleado));

    }
}
