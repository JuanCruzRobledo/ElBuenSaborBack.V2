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

        //===================== CAJERO ISABELLA =====================//

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
                .activo(true)
                .usuario(userIsabella)
                .sucursal(sucursal)
                .build();

        //===================== COCINERO MAITEN =====================//

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
                .activo(true)
                .usuario(userMaiten)
                .sucursal(sucursal)
                .build();

        //===================== COCINERO SANTIAGO =====================//

        UsuarioEntity userSantiago= UsuarioEntity.builder()
                .email("santiago@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.COCINERO).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        EmpleadoEntity empleado3 = EmpleadoEntity.builder()
                .nombre("Santiago")
                .apellido("Robledo")
                .telefono("3278282818")
                .activo(true)
                .usuario(userSantiago)
                .sucursal(sucursal)
                .build();

        //===================== DELIVERY EMILIANO =====================//

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


        EmpleadoEntity empleado4 = EmpleadoEntity.builder()
                .nombre("Emiliano")
                .apellido("Chavez")
                .telefono("23545222")
                .activo(true)
                .usuario(userEmiliano)
                .sucursal(sucursal)
                .build();

        //===================== ADMIN JUAN =====================//

        UsuarioEntity userJuan = UsuarioEntity.builder()
                .email("juan@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.ADMIN).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();


        EmpleadoEntity empleado5 = EmpleadoEntity.builder()
                .nombre("Juan Cruz")
                .apellido("Robledo")
                .telefono("23545222")
                .activo(true)
                .usuario(userJuan)
                .sucursal(sucursal)
                .build();



        empleadoRepository.saveAll(List.of(empleado1, empleado2, empleado3,empleado4,empleado5));
    }
}
