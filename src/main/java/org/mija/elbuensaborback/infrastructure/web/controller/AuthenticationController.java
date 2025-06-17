package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
import org.mija.elbuensaborback.application.dto.response.AuthResponse;
import org.mija.elbuensaborback.application.dto.response.ClienteBasicResponse;
import org.mija.elbuensaborback.application.mapper.ClienteMapper;
import org.mija.elbuensaborback.application.service.contratos.ClienteService;
import org.mija.elbuensaborback.infrastructure.persistence.entity.ClienteEntity;
import org.mija.elbuensaborback.infrastructure.persistence.entity.UsuarioEntity;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.ClienteJpaRepository;
import org.mija.elbuensaborback.infrastructure.persistence.repository.jpa.UsuarioJpaRepository;
import org.mija.elbuensaborback.infrastructure.security.service.AuthenticationService;
import org.mija.elbuensaborback.infrastructure.security.service.CustomUserDetailsService;
import org.mija.elbuensaborback.infrastructure.security.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;
    private final JwtService jwtService;
    private final ClienteJpaRepository clienteRepository;
    private final CustomUserDetailsService customUserDetailsService;
    private final ClienteMapper clienteMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        System.out.println("LOGIN");
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest nuevoUsuario) {
        System.out.println("REGISTER");
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(nuevoUsuario));
    }

    @GetMapping("/oauth2/success")
    public ResponseEntity<AuthResponse> loginSuccess() {
        System.out.println("ENTRO EN EL SUCCES");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken authToken) {
            AuthResponse respuesta = authService.oauth2Login(authToken);
            System.out.println("RESPUESTA "+ respuesta);
            return ResponseEntity.ok(respuesta);

        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/oauth2/google")
    public ResponseEntity<AuthResponse> authenticateGoogle(@RequestHeader("Authorization") String jwtToken) {
        try {
            // 1. Extraer email del token JWT generado por tu SuccessHandler
            String token = jwtToken.replace("Bearer ", "");
            String email = jwtService.extractUsername(token); // Implementa este método si no lo tienes

            // 2. Buscar el usuario en tu DB (asegúrate de que CustomUserDetails implementa UserDetails)
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            // 3. Generar NUEVO token con tu método existente (para mantener consistencia)
            String newJwt = jwtService.generateToken(userDetails); // <- Usa el método para UserDetails

            // 4. Obtener datos del cliente
            ClienteEntity cliente = clienteRepository.findByUsuarioEmail(email);

            return ResponseEntity.ok(new AuthResponse(newJwt, clienteMapper.toResponse(cliente)));

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Autenticación fallida", e);
        }
    }

    @PostMapping("/validate-google-token")
    public ResponseEntity<AuthResponse> validateGoogleToken(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractUsername(token.replace("Bearer ", ""));

        // Carga el usuario como UserDetails para generar token consistente
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
        String newToken = jwtService.generateToken(userDetails);

        ClienteEntity cliente = clienteRepository.findByUsuarioEmail(email);

        return ResponseEntity.ok(new AuthResponse(newToken, clienteMapper.toResponse(cliente)));
    }
}
