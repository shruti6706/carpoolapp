package com.carpoolapp.backend.controller;

import com.carpoolapp.backend.dto.CreateRideRequest;
import com.carpoolapp.backend.dto.RideResponse;
import com.carpoolapp.backend.service.RideService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;
    @PostMapping
    public ResponseEntity<RideResponse> createRide(
            @Valid @RequestBody
            CreateRideRequest request
    ){
        return ResponseEntity.ok(rideService.createRide(request));
    }


    @GetMapping
    public ResponseEntity<List<RideResponse>> getAllRides(){
        return ResponseEntity.ok(rideService.getAllRides());
    }

    @GetMapping("/my")
    public ResponseEntity<List<RideResponse>> getMyRides(){
        return ResponseEntity.ok(rideService.getMyRides());
    }

    @GetMapping("/{id:[0-9]+}")
    public ResponseEntity<RideResponse> getRideById(@PathVariable Long id){
        return ResponseEntity.ok(rideService.getRideById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<RideResponse>> searchRides(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam(required = false)
            Boolean womenOnly){
        return ResponseEntity.ok(rideService.searchRides(source, destination, womenOnly));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable Long id){
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }
}
