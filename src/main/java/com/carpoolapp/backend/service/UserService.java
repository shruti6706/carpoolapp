package com.carpoolapp.backend.service;

import com.carpoolapp.backend.dto.UpdateProfileRequest;
import com.carpoolapp.backend.dto.UserResponse;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    //gets the current logged -in user from security context
    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
                return userRepository.findByEmail(email)
                        .orElseThrow(()-> new RuntimeException("User not found"));

    }


    //SO basically 1stly we'll get our profile then we'll update it
    public UserResponse getMyProfile(){

        return UserResponse.from(getCurrentUser());

    }

    //Now we're going to update it
    public UserResponse updateMyProfile(UpdateProfileRequest request){

        User user = getCurrentUser();

        //update fields that were actually sent
        if(request.getName() != null)
            user.setName(request.getName());

        if(request.getPhone() != null)
            user.setPhone(request.getPhone());

        if(request.getProfilePicUrl() != null)
            user.setProfilePicUrl(request.getProfilePicUrl());

        if(request.getRole() != null)
            user.setRole(request.getRole());

        if(request.getGender() != null)
            user.setGender(request.getGender());

        return UserResponse.from(userRepository.save(user));
    }

}
