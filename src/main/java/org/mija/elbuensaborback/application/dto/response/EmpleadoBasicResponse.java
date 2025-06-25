package org.mija.elbuensaborback.application.dto.response;

public record EmpleadoBasicResponse(
        Long id,
        String nombre,
        String apellido,
        String telefono,
        String email,
        String rol
) implements PersonaResponse {}
