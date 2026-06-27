package com.carpoolapp.backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateRatingRequest {

    @NotNull
    private Long bookingId;

    @Min(1) @Max(5)
    private int stars;

    private String comment;

}
