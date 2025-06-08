package org.mija.elbuensaborback.infrastructure.configuration.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*");
        registry.addEndpoint("/ws-pedidos").setAllowedOriginPatterns("*");
    }


    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefijos para enviar y recibir mensajes
        registry.setApplicationDestinationPrefixes("/app"); // para enviar
        registry.enableSimpleBroker("/topic"); // para recibir
    }
}
