package org.mija.elbuensaborback.infrastructure.security.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${url.front}")
    private String urlFront;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String redirectUrl = urlFront + "/login?error=oauth_failed";
        new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
