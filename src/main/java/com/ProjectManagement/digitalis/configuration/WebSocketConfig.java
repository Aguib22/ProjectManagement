package com.ProjectManagement.digitalis.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
/*

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtAuthChannelInterceptor jwtAuthChannelInterceptor;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue"); // Canal pour envoyer des messages aux clients
        registry.setApplicationDestinationPrefixes("/app");// Préfixe pour les messages envoyés au serveur
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")  // Point de connexion WebSocket
                .setAllowedOrigins("*")  // Autoriser toutes les origines (CORS)
                .withSockJS(); // Supporte SockJS
    }
    */
/*@Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtAuthChannelInterceptor);
    }*//*

}
*/
