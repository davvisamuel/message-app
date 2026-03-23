package com.davvi.message_app.controller;

import com.davvi.message_app.dto.request.AuthLoginPostRequest;
import com.davvi.message_app.dto.request.AuthRegisterPostRequest;
import com.davvi.message_app.dto.response.AuthLoginPostResponse;
import com.davvi.message_app.dto.response.AuthRegisterPostResponse;
import com.davvi.message_app.mapper.AuthMapper;
import com.davvi.message_app.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthMapper authMapper;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterPostResponse> register(@RequestBody AuthRegisterPostRequest authRegisterPostRequest) {
        log.debug("Request received for register '{}'", authRegisterPostRequest);

        var user = authMapper.toUser(authRegisterPostRequest);

        var savedUser = authService.save(user);

        var authRegisterPostResponse = authMapper.toAuthRegisterPostResponse(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(authRegisterPostResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginPostResponse> login(@RequestBody AuthLoginPostRequest authLoginPostRequest) {

        var token = authService.login(authLoginPostRequest.username(), authLoginPostRequest.password());

        var authLoginPostResponse = authMapper.toAuthLoginPostResponse(token);

        return ResponseEntity.ok(authLoginPostResponse);
    }
}
