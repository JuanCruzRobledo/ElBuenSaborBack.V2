package org.mija.elbuensaborback.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
import org.mija.elbuensaborback.application.dto.response.AuthResponse;
import org.mija.elbuensaborback.infrastructure.security.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

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

    @PostMapping("/validate-google-token")
    public ResponseEntity<AuthResponse> validateGoogleToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.validateGoogleToken(token));
    }
}
