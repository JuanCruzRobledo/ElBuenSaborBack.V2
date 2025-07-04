package org.mija.elbuensaborback.infrastructure.persistence.data;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.LocalidadJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.SucursalJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteData {
    private final ClienteJpaRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleJpaRepository roleRepository;
    private final LocalidadJpaRepository localidadRepository;

    public void initClientWithUsers(){

        DomicilioEntity domicilio1 = DomicilioEntity.builder()
                .numero(123)
                .calle("Av. Jorgito ")
                .codigoPostal("1111")
                .activo(true)
                .localidad(localidadRepository.findByNombre("Godoy Cruz"))
                .descripcion("Casa blanca con rejas marrones")
                .build();

        UsuarioEntity userAmbar= UsuarioEntity.builder()
                .email("ambar@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.CLIENTE).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();

        ClienteEntity cliente1 = ClienteEntity.builder()
                .nombre("Ambar")
                .apellido("Gonzalez")
                .telefono("123456789")
                .usuario(userAmbar)
                .activo(true)
                .domicilio(List.of(domicilio1))
                .listaPedido(new ArrayList<>())
                .build();

        cliente1.setImagen(ImagenClienteEntity.builder().url("https://res.cloudinary.com/dlqx3atyg/image/upload/v1750939471/fotoambar_o7j9hk.webp").cliente(cliente1).build());

        /* CREATE CLIENTES */
        DomicilioEntity domicilio2 = DomicilioEntity.builder()
                .numero(123)
                .calle("Av. San Martin")
                .codigoPostal("5555")
                .activo(true)
                .descripcion("Casa Morada con paredes sin revocar")
                .localidad(localidadRepository.findByNombre("Godoy Cruz"))
                .build();

        UsuarioEntity userPerez = UsuarioEntity.builder()
                .email("perez@gmail.com")
                .password(passwordEncoder.encode("112233"))
                .authProviderEnum(AuthProviderEnum.LOCAL)
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .rol(roleRepository.findByRolEnum(RolEnum.ADMIN).orElseThrow(()-> new EntityNotFoundException("No se pudo encontrar el Rol")))
                .build();


        ClienteEntity cliente2 = ClienteEntity.builder()
                .nombre("Raton")
                .apellido("Perez")
                .telefono("987654321")
                .usuario(userPerez)
                .activo(true)
                .domicilio(List.of(domicilio2))
                .listaPedido(new ArrayList<>())
                .build();

        cliente2.setImagen(ImagenClienteEntity.builder().url("https://res.cloudinary.com/dlqx3atyg/image/upload/v1750939470/fotoJuan_ecihfp.webp").cliente(cliente2).build());

        clienteRepository.saveAll(List.of(cliente1, cliente2));
    }
}
