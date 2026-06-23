package com.carpoolapp.backend.controller;


import com.carpoolapp.backend.dto.BookingResponse;
import com.carpoolapp.backend.dto.CreateBookingRequest;
import com.carpoolapp.backend.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody
                                                         CreateBookingRequest request){

        return ResponseEntity.ok(
                bookingService.createBooking(request)
        );

    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<BookingResponse> acceptBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.acceptBooking(id));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<BookingResponse> rejectBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.rejectBooking(id));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/my")
    public ResponseEntity<List<BookingResponse>> getMyBookings(){
        return ResponseEntity.ok(bookingService.getMyBookingAsRider());
    }

    @GetMapping("/incoming")
    public ResponseEntity<List<BookingResponse>> getIncomingRequests(){
        return ResponseEntity.ok(bookingService.getIncomingRequestAsDriver());
    }

}
