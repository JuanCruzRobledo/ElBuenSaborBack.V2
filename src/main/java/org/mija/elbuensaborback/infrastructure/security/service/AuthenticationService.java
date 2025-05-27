package org.mija.elbuensaborback.infrastructure.security.service;

import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
import org.mija.elbuensaborback.application.dto.response.AuthResponse;
import org.mija.elbuensaborback.application.mapper.ClienteMapper;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.RoleEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.RoleRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.UsuarioRepositoryImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioRepositoryImpl usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepositoryImpl roleRepository;
    private final ClienteRepositoryImpl clienteRepository;
    private final ClienteMapper clienteMapper;

    public AuthenticationService(AuthenticationManager authManager, CustomUserDetailsService userDetailsService, UsuarioRepositoryImpl usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RoleRepositoryImpl roleRepository, ClienteRepositoryImpl clienteRepository, ClienteMapper clienteMapper) {
        this.authManager = authManager;
        this.userDetailsService = userDetailsService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.roleRepository = roleRepository;
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        ClienteEntity cliente = clienteRepository.findByUsuarioEmail(request.email());
        return new AuthResponse(token,clienteMapper.toResponse(cliente));
    }


    public AuthResponse register(RegisterRequest nuevoUsuario) {

        // Validar si el usuario ya existe
        if (usuarioRepository.findByEmail(nuevoUsuario.email()).isPresent()) {
            throw new BadCredentialsException("El correo ya está en uso.");
        }

        // Buscar rol CLIENTE
        RoleEntity role = roleRepository.findByRolEnum(RolEnum.CLIENTE)
                .orElseThrow(() -> new BadCredentialsException("El rol CLIENTE no existe."));

        // Crear entidad de usuario
        UsuarioEntity usuario = UsuarioEntity.builder()
                .email(nuevoUsuario.email())
                .password(passwordEncoder.encode(nuevoUsuario.password()))
                .rol(role)
                .disabled(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .build();

        // Crear entidad de cliente con usuario embebido
        ClienteEntity cliente = ClienteEntity.builder()
                .nombre(nuevoUsuario.nombre()) // asumimos que vienen en el request
                .apellido(nuevoUsuario.apellido())
                .telefono(nuevoUsuario.telefono())
                .usuario(usuario) // relación con usuario
                .build();


        clienteRepository.save(cliente);

        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.generateToken(userDetails);

        ClienteEntity clienteGuardado = clienteRepository.findByUsuarioEmail(nuevoUsuario.email());
        return new AuthResponse(token,clienteMapper.toResponse(cliente));
    }
}
