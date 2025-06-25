package org.mija.elbuensaborback.infrastructure.security.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ImagenClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.UsuarioRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.UsuarioJpaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsuarioRepositoryImpl usuarioRepository;
    private final RoleJpaRepository roleRepository;
    private final ClienteJpaRepository clienteRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. Cargar usuario desde Google
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        // 2. Extraer datos del usuario (email, nombre, etc.)
        String email = oAuth2User.getAttribute("email");
        String oauth2Id = oAuth2User.getAttribute("sub");  // ID único de Google

        // 3. Buscar o crear el usuario en tu DB
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    UsuarioEntity nuevoUsuario = new UsuarioEntity();
                    nuevoUsuario.setEmail(email);
                    nuevoUsuario.setOauth2Id(oauth2Id);
                    nuevoUsuario.setAuthProviderEnum(AuthProviderEnum.GOOGLE);
                    nuevoUsuario.setDisabled(false);
                    nuevoUsuario.setRol(getRole());

                    ClienteEntity nuevoCliente = new ClienteEntity();
                    nuevoCliente.setNombre(oAuth2User.getAttribute("given_name"));
                    nuevoCliente.setApellido(oAuth2User.getAttribute("family_name"));
                    nuevoCliente.setImagen(ImagenClienteEntity.builder().url(oAuth2User.getAttribute("picture")).build());
                    nuevoCliente.setTelefono(null);
                    nuevoCliente.setActivo(true);
                    nuevoCliente.setUsuario(nuevoUsuario);

                    clienteRepository.save(nuevoCliente);

                    // Asigna un rol por defecto (ej. ROLE_CLIENTE)
                    return usuarioRepository.findByEmail(email).orElse(nuevoUsuario);
                });

        // 4. Devolver el usuario en formato que Spring Security entienda
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getRolEnum().name())),
                oAuth2User.getAttributes(),
                "email"
        );
    }

    public RoleEntity getRole() {
        return roleRepository.findByRolEnum(RolEnum.CLIENTE).orElseThrow(()-> new EntityNotFoundException("No se pudo asignar un rol"));
    }
}