package com.davvi.message_app.security;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandshakeInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        var optionalToken = recoverToken(request);

        if (optionalToken.isEmpty()) return false;

        tokenService.validateToken(optionalToken.get());

        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

    }

    public Optional<String> recoverToken(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();

        var authenticationHeader = headers.getFirst("Authentication");

        if (authenticationHeader == null) return Optional.empty();

        return Optional.of(authenticationHeader.replace("Bearer", "").trim());
    }
}
