package org.mija.elbuensaborback.infrastructure.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mija.elbuensaborback.application.dto.request.auth.AuthRequest;
import org.mija.elbuensaborback.application.dto.request.auth.RegisterRequest;
import org.mija.elbuensaborback.application.dto.request.usuario.UsuarioChangePasswordRequest;
import org.mija.elbuensaborback.application.dto.response.AuthResponse;
import org.mija.elbuensaborback.infrastructure.security.service.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Map;


// Controlador REST responsable de gestionar las operaciones de autenticación del sistema.
// Las rutas expuestas comienzan con /auth.
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    // Inyección del servicio de autenticación que contiene la lógica de negocio.
    private final AuthenticationService authService;

    // Endpoint para realizar el login tradicional (usuario y contraseña).
    // Genera un token JWT y lo devuelve como una cookie HttpOnly.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {
        System.out.println("LOGIN solicitado para email: " + request.email());

        // Se realiza la autenticación y se obtiene el token y los datos del usuario.
        AuthResponse authResponse = authService.login(request);

        // Se construye la cookie que contiene el JWT.
        ResponseCookie cookie = ResponseCookie.from("token", authResponse.token())
                .httpOnly(true)          // Protege el token de accesos por JS del lado cliente.
                .secure(false)           // Debe ser true en producción con HTTPS.
                .path("/")               // La cookie estará disponible para todas las rutas.
                .sameSite("Strict")      // Previene el envío de la cookie en solicitudes cross-site.
                .maxAge(Duration.ofDays(1)) // Duración de la cookie (1 día).
                .build();

        // Se agrega la cookie en el encabezado de la respuesta.
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        System.out.println("LOGIN exitoso para usuario: " + authResponse.user());

        // Se devuelve el usuario autenticado en el cuerpo de la respuesta.
        return ResponseEntity.ok(Map.of("user", authResponse.user()));
    }

    // Endpoint para registrar un nuevo usuario (cliente).
    // Luego del registro exitoso, se emite un JWT y se devuelve como cookie.
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest nuevoUsuario, HttpServletResponse response) {
        // Se registra el nuevo usuario y se genera un JWT.
        AuthResponse authResponse = authService.register(nuevoUsuario);

        // Se crea la cookie con el token JWT.
        ResponseCookie cookie = ResponseCookie.from("token", authResponse.token())
                .httpOnly(true)
                .secure(false)           // Asegurarse de usar true en entornos productivos (HTTPS).
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(1))
                .build();

        // Se incluye la cookie en la respuesta.
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Se devuelve el usuario registrado en el cuerpo de la respuesta.
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("user", authResponse.user()));
    }

    // Endpoint para validar un token JWT de Google (utilizado en OAuth login).
    // El token se obtiene desde una cookie, y se renueva automáticamente si es válido.
    @PostMapping("/validate-google-token")
    public ResponseEntity<?> validateGoogleToken(@CookieValue(name = "token", required = false) String token, HttpServletResponse response) {
        // Si no se encuentra la cookie 'token', se rechaza la petición.
        if (token == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no presente");
        }

        // Se valida el token y se genera uno nuevo.
        AuthResponse authResponse = authService.validateGoogleToken(token);

        // Se renueva la cookie con un nuevo token (opcional, útil para extender sesión).
        ResponseCookie cookie = ResponseCookie.from("token", authResponse.token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(1))
                .build();

        // Se agrega la cookie renovada en la respuesta.
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Se retorna el usuario validado.
        return ResponseEntity.ok(Map.of("user", authResponse.user()));
    }

    // Endpoint para cambiar la contraseña del usuario.
    // Requiere el ID de la persona y las credenciales actuales + nueva contraseña.
    // Si el cambio es exitoso, se genera un nuevo JWT y se actualiza la cookie.
    @PatchMapping("/{id}/change-password")
    public ResponseEntity<?> cambiarContrasena(
            @PathVariable Long id,
            @RequestBody UsuarioChangePasswordRequest newUserCredentials,
            HttpServletResponse response
    ) {
        // Se cambia la contraseña y se obtiene un nuevo token + usuario.
        AuthResponse authResponse = authService.changePassword(id, newUserCredentials);

        // Se actualiza la cookie con el nuevo token para reflejar el cambio.
        ResponseCookie cookie = ResponseCookie.from("token", authResponse.token())
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Strict")
                .maxAge(Duration.ofDays(1))
                .build();

        // Se actualiza la cookie en la respuesta.
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Se retorna el usuario actualizado.
        return ResponseEntity.ok(Map.of("user", authResponse.user()));
    }

    // Endpoint para cerrar sesión (logout).
    // Elimina la cookie del token configurándola con duración 0.
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Se crea una cookie vacía con duración cero para forzar su eliminación.
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)           // Debe ser true en producción.
                .path("/")
                .sameSite("Strict")
                .maxAge(0)               // Elimina la cookie inmediatamente.
                .build();

        // Se añade la cookie en la respuesta para sobreescribir la anterior.
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Se devuelve un mensaje de éxito.
        return ResponseEntity.ok().build();
    }
}