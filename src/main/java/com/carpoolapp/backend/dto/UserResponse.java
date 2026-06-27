package com.carpoolapp.backend.dto;

import com.carpoolapp.backend.entity.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private  String phone;
    private String profilePicUrl;
    private User.Gender gender;
    private User.Role role;
    private  boolean verified;
    private LocalDateTime createdAt;


    public static UserResponse from(User user){
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setProfilePicUrl(user.getProfilePicUrl());
        response.setGender(user.getGender());
        response.setRole(user.getRole());
        response.setVerified(user.isVerified());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

}
