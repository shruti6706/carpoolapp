package com.carpoolapp.backend.dto;


import com.carpoolapp.backend.entity.Booking;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponse {

    private Long id;
    private int seatsBooked;
    private Booking.BookingStatus status;
    private LocalDateTime createdAt;

    private Long rideId;
    private String source;
    private String destination;
    private LocalDateTime departureTime;

    private Long riderId;
    private String riderName;
    private String riderPhone;

    private Long driverId;
    private String driverName;

    public static BookingResponse from(Booking booking){
        BookingResponse response = new BookingResponse();

        response.setId(booking.getId());
        response.setSeatsBooked(booking.getSeatsBooked());
        response.setStatus(booking.getStatus());

        response.setRideId(booking.getRide().getId());
        response.setSource(booking.getRide().getSource());
        response.setDestination(booking.getRide().getDestination());
        response.setDepartureTime(booking.getRide().getDepartureTime());
        response.setCreatedAt(booking.getCreatedAt());
        response.setRiderId(booking.getRider().getId());
        response.setRiderName(booking.getRider().getName());
        response.setRiderPhone(booking.getRider().getPhone());

        response.setDriverId(booking.getRide().getDriver().getId());
        response.setDriverName(booking.getRide().getDriver().getName());

        return response;
    }

}
