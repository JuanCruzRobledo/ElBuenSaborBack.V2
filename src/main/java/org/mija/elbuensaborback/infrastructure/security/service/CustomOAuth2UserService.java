package org.mija.elbuensaborback.infrastructure.security.service;

import jakarta.persistence.EntityNotFoundException;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.RoleJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.UsuarioJpaRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UsuarioJpaRepository usuarioRepository;
    private final RoleJpaRepository roleRepository;

    public CustomOAuth2UserService(UsuarioJpaRepository usuarioRepository, RoleJpaRepository roleRepository) {
        this.usuarioRepository = usuarioRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
        String email = oAuth2User.getAttribute("email");
        String oauth2Id = oAuth2User.getAttribute("sub"); // Google usa "sub"

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    // Crear nuevo usuario si no existe
                    UsuarioEntity nuevo = new UsuarioEntity();
                    nuevo.setEmail(email);
                    nuevo.setOauth2Id(oauth2Id);
                    nuevo.setAuthProviderEnum(AuthProviderEnum.valueOf(provider));
                    nuevo.setRol(defaultRole()); // Asigna un rol por defecto
                    return usuarioRepository.save(nuevo);
                });

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(usuario.getRol().getRolEnum().name())),
                oAuth2User.getAttributes(),
                "email"
        );
    }

    private RoleEntity defaultRole() {
        // Devuelve un rol por defecto, como "ROLE_CLIENTE"
        return roleRepository.findByRolEnum(RolEnum.CLIENTE).orElseThrow(()->new EntityNotFoundException("No se encontro el rol cliente"));
    }
}