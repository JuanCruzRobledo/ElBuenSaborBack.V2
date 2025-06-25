package org.mija.elbuensaborback.infrastructure.security.service;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
            case "EMPLEADO" -> {
                if (!(persona instanceof EmpleadoEntity empleado))
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No es un empleado");

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

    public AuthResponse oauth2Login(OAuth2AuthenticationToken authToken) {
        OAuth2User oAuth2User = authToken.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        UsuarioEntity usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        ClienteEntity cliente = clienteRepository.findByUsuarioEmail(usuario.getEmail());

        String jwt = jwtService.generateToken(oAuth2User);

        ClienteBasicResponse clienteResponse = new ClienteBasicResponse(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getApellido(),
                cliente.getTelefono(),
                usuario.getEmail(),
                cliente.getImagen().getUrl()
        );
        return new AuthResponse(jwt, clienteResponse);
    }

}
