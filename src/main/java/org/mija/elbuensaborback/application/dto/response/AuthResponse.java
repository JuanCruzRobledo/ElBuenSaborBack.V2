package org.mija.elbuensaborback.application.dto.response;

public record AuthResponse(
        String token,
        PersonaResponse user
) {}
