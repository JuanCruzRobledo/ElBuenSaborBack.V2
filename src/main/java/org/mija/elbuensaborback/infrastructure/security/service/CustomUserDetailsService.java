package org.mija.elbuensaborback.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.UsuarioRepositoryImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositoryImpl usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + email + " no encontrado"));

        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getRolEnum().name())
        );

        // Usa password vacío si es usuario OAuth2
        String password = usuario.getAuthProviderEnum() == AuthProviderEnum.LOCAL
                ? usuario.getPassword()
                : "[OAUTH2_USER]";

        return User.builder()
                .username(usuario.getEmail())
                .password(password) // Nunca null
                .disabled(usuario.isDisabled())
                .accountExpired(usuario.isAccountExpired())
                .accountLocked(usuario.isAccountLocked())
                .credentialsExpired(usuario.isCredentialsExpired())
                .authorities(authorities)
                .build();
    }
}
