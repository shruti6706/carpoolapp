package com.carpoolapp.backend.dto;


import com.carpoolapp.backend.entity.Rating;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RatingResponse {

    private Long id;
    private int stars;
    private String comment;
    private LocalDateTime createdAt;

    //Rater info
    private Long raterId;
    private String raterName;

    //Ratee info
    private Long rateeId;
    private String rateeName;

    //Booking/ride context
    private Long bookingId;
    private String rideSource;
    private String rideDestination;


    public static RatingResponse from(Rating rating){
        RatingResponse response = new RatingResponse();

        response.setId(rating.getId());
        response.setStars(rating.getStars());
        response.setComment(rating.getComment());
        response.setCreatedAt(rating.getCreatedAt());

        response.setRaterId(rating.getRater().getId());
        response.setRaterName(rating.getRater().getName());

        response.setRateeId(rating.getRatee().getId());
        response.setRateeName(rating.getRatee().getName());

        response.setBookingId(rating.getBooking().getId());
        response.setRideSource(rating.getBooking().getRide().getSource());
        response.setRideDestination(rating.getBooking().getRide().getDestination());

        return response;
    }



}
