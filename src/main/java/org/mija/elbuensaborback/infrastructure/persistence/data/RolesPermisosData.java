package org.mija.elbuensaborback.infrastructure.persistence.data;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolesPermisosData {

    private final RoleJpaRepository roleRepository;

    public void initRoleAndPermission(){
        // CREATE ROLES
        RoleEntity roleAdmin = RoleEntity.builder()
                .rolEnum(RolEnum.ADMIN)
                .build();

        RoleEntity roleCliente = RoleEntity.builder()
                .rolEnum(RolEnum.CLIENTE)
                .build();

        RoleEntity roleCajero = RoleEntity.builder()
                .rolEnum(RolEnum.CAJERO)
                .build();

        RoleEntity roleCocinero = RoleEntity.builder()
                .rolEnum(RolEnum.COCINERO)
                .build();

        RoleEntity roleDelivery = RoleEntity.builder()
                .rolEnum(RolEnum.DELIVERY)
                .build();

        roleRepository.saveAll(List.of(roleAdmin, roleCliente, roleCajero, roleCocinero, roleDelivery));
    }
}
