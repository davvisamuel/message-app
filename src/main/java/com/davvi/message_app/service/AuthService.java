package com.davvi.message_app.service;

import com.davvi.message_app.domain.User;
import com.davvi.message_app.domain.UserRole;
import com.davvi.message_app.exception.UsernameAlreadyExists;
import com.davvi.message_app.repository.UserRepository;
import com.davvi.message_app.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public User save(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExists("Esse nome de usuário não está disponível!");
        }

        user.setRole(UserRole.ROLE_USER);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public String login(String username, String password) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        var authenticate = authenticationManager.authenticate(authenticationToken);

        var user = (User) authenticate.getPrincipal();

        return tokenService.generationToken(user);
    }
}
