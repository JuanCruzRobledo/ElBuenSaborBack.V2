package org.mija.elbuensaborback.infrastructure.security.service;

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
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepositoryImpl usuarioRepository;

    public CustomUserDetailsService(UsuarioRepositoryImpl usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Email "+email + " no encontrado"));

        List<SimpleGrantedAuthority> listaAuthority = new ArrayList<>();

        listaAuthority.add(new SimpleGrantedAuthority("ROLE_".concat(usuario.getRol().getRolEnum().name())));


        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .disabled(usuario.isDisabled())
                .accountExpired(usuario.isAccountExpired())
                .accountLocked(usuario.isAccountLocked())
                .credentialsExpired(usuario.isCredentialsExpired())
                .authorities(listaAuthority)
                .build();
    }
}
