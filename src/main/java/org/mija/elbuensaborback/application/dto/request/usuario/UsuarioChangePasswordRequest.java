package org.mija.elbuensaborback.application.dto.request.usuario;

public record UsuarioChangePasswordRequest(
        String email,
        String currentPassword,
        String newPassword
) {
}
