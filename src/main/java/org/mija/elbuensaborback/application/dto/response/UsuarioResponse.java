package org.mija.elbuensaborback.application.dto.response;

import org.mija.elbuensaborback.application.dto.global.rol.RolDto;
import org.mija.elbuensaborback.domain.enums.AuthProviderEnum;

public record UsuarioResponse(
        String email,
        String oauth2Id,
        AuthProviderEnum authProviderEnum,
        boolean disabled,
        boolean accountExpired,
        boolean accountLocked,
        boolean credentialsExpired,
        String password,
        RolDto rol
) {
}
