package com.carpoolapp.backend.dto;


import com.carpoolapp.backend.entity.Ride;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RideResponse {

    private Long id;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private int availableSeats;
    private Double pricePerSeat;
    private boolean womenOnly;
    private Ride.RideStatus status;
    private LocalDateTime createdAt;

    private Long driverId;
    private String driverName;
    private String driverPhone;

    public static RideResponse from(Ride ride){
        RideResponse response = new RideResponse();

        //The info which user has given: user Entity -> RideResponse DTO
        response.setId(ride.getId());
        response.setSource(ride.getSource());
        response.setDestination(ride.getDestination());
        response.setDepartureTime(ride.getDepartureTime());
        response.setAvailableSeats(ride.getAvailableSeats());
        response.setPricePerSeat(ride.getPricePerSeat());
        response.setWomenOnly(ride.isWomenOnly());
        response.setStatus(ride.getStatus());
        response.setCreatedAt(ride.getCreatedAt());
        response.setDriverId(ride.getDriver().getId());
        response.setDriverName(ride.getDriver().getName());
        response.setDriverPhone(ride.getDriver().getPhone());
        return response;
    }

}
