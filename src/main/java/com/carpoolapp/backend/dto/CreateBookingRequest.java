package com.carpoolapp.backend.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateBookingRequest {

    @NotNull
    private Long rideId;

    @Positive
    private int seatsBooked;

}
