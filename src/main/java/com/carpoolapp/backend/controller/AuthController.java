package com.carpoolapp.backend.controller;

import com.carpoolapp.backend.dto.AuthResponse;
import com.carpoolapp.backend.dto.LoginRequest;
import com.carpoolapp.backend.dto.RegisterRequest;
import com.carpoolapp.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth") // without these postman will show: http://localhost:8080/register causing 403 forbidden error
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody
                                                 RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody
                                                  LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }
}
