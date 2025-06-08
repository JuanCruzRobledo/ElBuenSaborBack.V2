package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.EmpleadoEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.SucursalEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoData {
    private final EmpleadoJpaRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleJpaRepository roleRepository;
    private final SucursalJpaRepository sucursalRepository;

    public void initEmpleadoWithUser(){
        SucursalEntity sucursal = sucursalRepository.findById(1L).orElseThrow(()-> new EntityNotFoundException("No se encontro la sucursal"));

        UsuarioEntity userIsabella = UsuarioEntity.builder()
                .email("isabella@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.EMPLEADO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        UsuarioEntity userMaiten = UsuarioEntity.builder()
                .email("maiten@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.EMPLEADO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        EmpleadoEntity empleado1 = EmpleadoEntity.builder()
                .nombre("Isabella")
                .apellido("Lopez")
                .telefono("555111222")
                .usuario(userIsabella)
                .sucursal(sucursal) // asigná una sucursal si tenés
                .build();

        EmpleadoEntity empleado2 = EmpleadoEntity.builder()
                .nombre("Maiten")
                .apellido("Fernandez")
                .telefono("444333222")
                .usuario(userMaiten)
                .sucursal(sucursal) // asigná una sucursal si tenés
                .build();

        empleadoRepository.saveAll(List.of(empleado1, empleado2));
    }
}
