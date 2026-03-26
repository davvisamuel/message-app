package com.davvi.message_app.security;

import com.davvi.message_app.domain.User;
import com.davvi.message_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        assert accessor != null;

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authorization = accessor.getFirstNativeHeader("Authorization");
            assert authorization != null;

            var token = authorization.replace("Bearer ", "").trim();

            String userId = tokenService.validateToken(token);

            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow();

            var authentication = new UsernamePasswordAuthenticationToken
                    (user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            accessor.setUser(authentication);
        }

        return message;
    }
}
