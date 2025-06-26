package org.mija.elbuensaborback.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
import org.mija.elbuensaborback.application.dto.request.usuario.UsuarioChangePasswordRequest;
import org.mija.elbuensaborback.application.dto.response.AuthResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.mapper.ClienteMapper;
import org.mija.elbuensaborback.application.mapper.EmpleadoMapper;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;
import org.mija.elbuensaborback.domain.enums.RolEnum;
import org.mija.elbuensaborback.infrastructure.persistence.entity.*;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.ClienteRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.PersonaRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.RoleRepositoryImpl;
import org.mija.elbuensaborback.infrastructure.persistence.repository.adapter.UsuarioRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import static com.cloudinary.AccessControlRule.AccessType.token;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authManager;
    private final CustomUserDetailsService userDetailsService;
    private final UsuarioRepositoryImpl usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepositoryImpl roleRepository;
    private final ClienteRepositoryImpl clienteRepository;
    private final ClienteMapper clienteMapper;
    private final PersonaRepositoryImpl personaRepository;
    private final EmpleadoMapper empleadoMapper;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        //Para la respuesta del login:
        //Buscamos la persona que está asociado a ese usuario
        PersonaEntity persona = personaRepository.findByUsuarioEmail(request.email());

        // Validación de estado activo
        if (!persona.isActivo()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no está activo.");
        }

        //Dependiendo el tipo de persona devuelve su respuesta
        switch (request.tipoLogin()) {
            case "CLIENTE" -> {
                if (!(persona instanceof ClienteEntity cliente))
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No es un cliente");

                return new AuthResponse(token, clienteMapper.toBasicResponse(cliente));
            }
            case "ADMIN" -> {
                if (!(persona instanceof EmpleadoEntity empleado))
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No es un administrador");

                return new AuthResponse(token, empleadoMapper.toBasicResponse(empleado));
            }
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de login inválido");
        }
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
                .authProviderEnum(AuthProviderEnum.LOCAL)
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
                .activo(true)
                .usuario(usuario) // relación con usuario
                .build();


        clienteRepository.save(cliente);

        // Generar token
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token,clienteMapper.toBasicResponse(cliente));
    }

    public AuthResponse validateGoogleToken(String tokenHeader) {
        String token = tokenHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);

        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String newToken = jwtService.generateToken(userDetails);

        ClienteEntity cliente = clienteRepository.findByUsuarioEmail(email);

        if (!cliente.isActivo()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no está activo.");
        }

        return new AuthResponse(newToken, clienteMapper.toBasicResponse(cliente));
    }

    public AuthResponse changePassword(Long personaId, UsuarioChangePasswordRequest request) {
        PersonaEntity persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));

        UsuarioEntity usuario = persona.getUsuario();

        // Verificamos que el email coincida
        if (!usuario.getEmail().equals(request.email())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El email no coincide");
        }

        // Verificamos que la contraseña actual sea correcta
        if (!passwordEncoder.matches(request.currentPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La contraseña actual es incorrecta");
        }

        // Actualizamos la contraseña
        usuario.setPassword(passwordEncoder.encode(request.newPassword()));
        usuarioRepository.save(usuario);

        // Generar nuevo token
        UserDetails userDetails = userDetailsService.loadUserByUsername(usuario.getEmail());
        String token = jwtService.generateToken(userDetails);

        // Devolver AuthResponse según tipo de persona
        if (persona instanceof ClienteEntity cliente) {
            return new AuthResponse(token, clienteMapper.toBasicResponse(cliente));
        } else if (persona instanceof EmpleadoEntity empleado) {
            return new AuthResponse(token, empleadoMapper.toBasicResponse(empleado));
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Tipo de persona desconocido");
        }
    }

}
