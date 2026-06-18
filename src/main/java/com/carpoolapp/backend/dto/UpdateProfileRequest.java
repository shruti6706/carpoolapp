package com.carpoolapp.backend.dto;

import com.carpoolapp.backend.entity.User;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    private String name;
    private String phone;
    private String profilePicUrl;
    private User.Role role;
    private User.Gender gender;

}
