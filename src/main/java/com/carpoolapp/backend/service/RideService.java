package com.carpoolapp.backend.service;


import com.carpoolapp.backend.dto.CreateRideRequest;
import com.carpoolapp.backend.dto.RideResponse;
import com.carpoolapp.backend.entity.Ride;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.repository.RideRepository;
import com.carpoolapp.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RideService {

    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public RideResponse createRide(CreateRideRequest request){
        User driver = getCurrentUser();

        if(driver.getRole() == User.Role.RIDER){
            throw new RuntimeException("Only drivers can create rides");
        }

        Ride ride = new Ride();
        ride.setDriver(driver);
        ride.setSource(request.getSource());
        ride.setDestination(request.getDestination());
        ride.setDepartureTime(request.getDepartureTime());
        ride.setAvailableSeats(request.getAvailableSeats());
        ride.setPricePerSeat(request.getPricePerSeat());
        ride.setWomenOnly(request.isWomenOnly());

        return RideResponse.from(rideRepository.save(ride));

    }

    public RideResponse getRideById(Long id){
        Ride ride = rideRepository.findByIdWithDriver(id)
                .orElseThrow(()-> new RuntimeException("Ride not found"));
        return RideResponse.from(ride);
    }

    public List<RideResponse> searchRides(String source, String destination, Boolean womenOnly){
        List<Ride> rides;
        if(womenOnly != null){
            rides = rideRepository.searchRidesWithFilter(source, destination, womenOnly);
        } else{
            rides = rideRepository.searchRides(source, destination);
        }
        return rides.stream().map(RideResponse::from).toList();
    }

    public void deleteRide(Long id){
        User currentUser = getCurrentUser();

        Ride ride = rideRepository.findByIdWithDriver(id)
                .orElseThrow(()-> new RuntimeException("Ride nto found"));

        if(!ride.getDriver().getId().equals(currentUser.getId())){
            throw new RuntimeException("You can delete only your own rides");
        }

        rideRepository.delete(ride);
    }

    public List<RideResponse> getMyRides() {
        User currentUser = getCurrentUser();
        return rideRepository.findByDriverIdWithDriver(currentUser.getId())
                .stream()
                .map(RideResponse::from)
                .toList();
    }

    public List<RideResponse> getAllRides() {
        return rideRepository.findAllWithDriver()
                .stream()
                .map(RideResponse::from)
                .toList();
    }
}
