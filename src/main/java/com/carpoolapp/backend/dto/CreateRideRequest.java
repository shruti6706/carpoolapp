package com.carpoolapp.backend.dto;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRideRequest {

    //Creating Ride request: taking info from user
    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    @Future
    private LocalDateTime departureTime;

    @Positive
    private int availableSeats;

    @Positive
    private Double pricePerSeat;

    private boolean womenOnly = false;
}
