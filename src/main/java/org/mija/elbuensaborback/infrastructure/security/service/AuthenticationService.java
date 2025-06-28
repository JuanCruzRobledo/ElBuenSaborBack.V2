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
        try {
            System.out.println("Autenticando usuario: " + request.email());

            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);

            PersonaEntity persona = personaRepository.findByUsuarioEmail(request.email());

            System.out.println("PERSONA: " + persona.getApellido() + " " + persona.getNombre() + " - " + persona.getUsuario().getRol().getRolEnum().name());

            if (!persona.isActivo()) {
                System.out.println("ERROR: Usuario no está activo.");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "El usuario no está activo.");
            }

            switch (request.tipoLogin()) {
                case "CLIENTE" -> {
                    System.out.println("Login tipo CLIENTE");
                    if (!(persona instanceof ClienteEntity cliente)) {
                        System.out.println("ERROR: No es un cliente.");
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No es un cliente");
                    }
                    return new AuthResponse(token, clienteMapper.toBasicResponse(cliente));
                }
                case "ADMIN" -> {
                    System.out.println("Login tipo ADMIN");
                    if (!(persona instanceof EmpleadoEntity empleado)) {
                        System.out.println("ERROR: No es un administrador.");
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No es un administrador");
                    }
                    return new AuthResponse(token, empleadoMapper.toBasicResponse(empleado));
                }
                default -> {
                    System.out.println("ERROR: Tipo de login inválido: " + request.tipoLogin());
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de login inválido");
                }
            }
        } catch (Exception e) {
            System.out.println("ERROR en login: " + e.getMessage());
            e.printStackTrace();  // imprime la pila para mayor detalle
            throw e; // re-lanzar para que el controlador lo maneje (o para que Spring devuelva el error)
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

    public AuthResponse validateGoogleToken(String token) {

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
