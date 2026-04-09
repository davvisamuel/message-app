package com.davvi.message_app.security;

import com.davvi.message_app.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var optionalToken = recoverToken(request);

        if (optionalToken.isPresent()) {
            var subjectId = tokenService.validateToken(optionalToken.get());
            var user = userRepository.findById(Long.parseLong(subjectId)).orElseThrow();
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    public Optional<String> recoverToken(HttpServletRequest request) {
        var authenticationHeader = request.getHeader("Authorization");

        if (authenticationHeader == null) return Optional.empty();

        return Optional.of(authenticationHeader.replace("Bearer", "").trim());
    }
}
