package com.carpoolapp.backend.service;

import com.carpoolapp.backend.dto.AuthResponse;
import com.carpoolapp.backend.dto.LoginRequest;
import com.carpoolapp.backend.dto.RegisterRequest;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.repository.UserRepository;
import com.carpoolapp.backend.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //register request will come here
    public AuthResponse register(RegisterRequest request){
        //check if registering email already existed ?
        if(userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException(("Email already registered"));
        }

        //Now if it is not registered build new User Object from request

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        //hash password before saving
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setGender(request.getGender());
        user.setRole(request.getRole() != null?request.getRole() : User.Role.RIDER);

        //Saves ro DB, triggers @PrePersist
        userRepository.save(user);

        //Now generate token for new registered user
        String token = jwtService.generateToken(user.getEmail());
        //return response to user
        return new AuthResponse(token, user.getName(),
                user.getEmail(), user.getRole().name());

    }

    //If account already existed authenticate
    public AuthResponse login(LoginRequest request){
        // This checks email + paasword - throws exception if wrong
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        //give token to user
        String token = jwtService.generateToken(user.getEmail());
        return new AuthResponse(token, user.getName(), user.getEmail(), user.getRole().name());

    }
}
