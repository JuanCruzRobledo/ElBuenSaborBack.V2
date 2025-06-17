package org.mija.elbuensaborback.infrastructure.security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Value("${url.front}")
    private String urlFront;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 1. Obtener el usuario autenticado por Google
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        // 2. Generar JWT usando el método específico para OAuth2User
        String jwt = jwtService.generateToken(oAuth2User);

        // 3. Redirigir al frontend con el token
        String redirectUrl = urlFront + "/auth-redirect?token=" + jwt;
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}