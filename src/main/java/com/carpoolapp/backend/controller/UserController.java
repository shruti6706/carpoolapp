package com.carpoolapp.backend.controller;

import com.carpoolapp.backend.dto.UpdateProfileRequest;
import com.carpoolapp.backend.dto.UserResponse;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(){
        return ResponseEntity.ok(userService.getMyProfile());
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateMyProfile(
            @RequestBody UpdateProfileRequest request
            ){
        return ResponseEntity.ok(userService.updateMyProfile(request));
    }
}
