package org.mija.elbuensaborback.application.dto.request.auth;

public record RegisterRequest(
        String username,
        String password,
        String nombre,
        String apellido,
        String email,
        String telefono
) {}