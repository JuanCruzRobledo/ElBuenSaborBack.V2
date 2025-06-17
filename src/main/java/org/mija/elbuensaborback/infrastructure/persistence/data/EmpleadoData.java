package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
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
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.CAJERO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        EmpleadoEntity empleado1 = EmpleadoEntity.builder()
                .nombre("Isabella")
                .apellido("Lopez")
                .telefono("555111222")
                .usuario(userIsabella)
                .sucursal(sucursal)
                .build();


        UsuarioEntity userMaiten = UsuarioEntity.builder()
                .email("maiten@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.COCINERO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();


        EmpleadoEntity empleado2 = EmpleadoEntity.builder()
                .nombre("Maiten")
                .apellido("Fernandez")
                .telefono("444333222")
                .usuario(userMaiten)
                .sucursal(sucursal)
                .build();

        UsuarioEntity userEmiliano = UsuarioEntity.builder()
                .email("emiliano@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.DELIVERY).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();


        EmpleadoEntity empleado3 = EmpleadoEntity.builder()
                .nombre("Emiliano")
                .apellido("Chavez")
                .telefono("23545222")
                .usuario(userEmiliano)
                .sucursal(sucursal)
                .build();

        empleadoRepository.saveAll(List.of(empleado1, empleado2, empleado3));
    }
}
