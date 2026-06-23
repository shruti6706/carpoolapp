package com.carpoolapp.backend.service;

import com.carpoolapp.backend.dto.BookingResponse;
import com.carpoolapp.backend.dto.CreateBookingRequest;
import com.carpoolapp.backend.entity.Booking;
import com.carpoolapp.backend.entity.Ride;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.repository.BookingRepository;
import com.carpoolapp.backend.repository.RideRepository;
import com.carpoolapp.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RideRepository rideRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request){
        User rider = getCurrentUser();

        Ride ride = rideRepository.findByIdWithDriver(request.getRideId())
                .orElseThrow(()-> new RuntimeException("Ride not found"));

        if(ride.getDriver().getId().equals(rider.getId())){
            throw new RuntimeException("You cannot book your own ride");
        }

        //Women-only rides enforcement
        if(ride.isWomenOnly() && rider.getGender() != User.Gender.FEMALE){
            throw new RuntimeException("This ride is reserved only for women riders only");
        }

        //prevent duplicate active request on same ride by same rider
        bookingRepository.findActiveByRiderAndRide(rider.getId(),ride.getId())
                .ifPresent(b-> {
                    throw new RuntimeException("You already have " +
                            "an active booking request for this ride");
                });

        if(request.getSeatsBooked() > ride.getAvailableSeats()){
            throw new RuntimeException("Not enough available seats on this ride");
        }

        Booking booking = new Booking();
        booking.setRider(rider);
        booking.setRide(ride);
        booking.setSeatsBooked(request.getSeatsBooked());

        Booking saved = bookingRepository.save(booking);
        Booking withDetails = bookingRepository.findByIdWithDetails(saved.getId())
                .orElseThrow(()-> new RuntimeException("Booking not found after save"));

        return BookingResponse.from(withDetails);
    }
    @Transactional
    public BookingResponse acceptBooking(Long bookingId){

        User driver = getCurrentUser();
        //FInd booking is present or not
        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        //Find driver who is accepting booking is the one who created ride
        if(!booking.getRide().getDriver().getId().equals(driver.getId())){
            throw new RuntimeException("You can accept only requests from your own ride");
        }

        //Check the status of booking because it will affect on seats
        if(booking.getStatus() != Booking.BookingStatus.PENDING){
            throw new RuntimeException("Only Pending requests can be accepted");
        }

        Ride ride = booking.getRide();
        //check is seats booked are greater than available seats
        if(booking.getSeatsBooked() > ride.getAvailableSeats()){
            throw new RuntimeException("Not enough seats available to accept this request");
        }

        //Now, after checking all thing's do changes in availableSeats
        ride.setAvailableSeats(ride.getAvailableSeats() - booking.getSeatsBooked());
        rideRepository.save(ride);

        //accept the ride and save it in db
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        Booking updated = bookingRepository.save(booking);

        return BookingResponse.from(updated);
    }

    @Transactional
    public BookingResponse rejectBooking(Long bookingId){

        User driver = getCurrentUser();
        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        if(!booking.getRide().getDriver().getId().equals(driver.getId())){
            throw new RuntimeException("You can only reject requests from your own ride");
        }

        if(booking.getStatus() != Booking.BookingStatus.PENDING){
            throw new RuntimeException("Only pending requests can be rejected");
        }

        booking.setStatus(Booking.BookingStatus.REJECTED);
        Booking updated = bookingRepository.save(booking);

        return BookingResponse.from(updated);
    }

    @Transactional
    public BookingResponse cancelBooking(Long bookingId){

        User rider = getCurrentUser();

        Booking booking = bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        if(!booking.getRider().getId().equals(rider.getId())){
            throw new RuntimeException("You can cancel only your own bookings");
        }

        if(booking.getStatus() == Booking.BookingStatus.CANCELLED
        || booking.getStatus() == Booking.BookingStatus.REJECTED){
            throw new RuntimeException("this booking is already cancelled or rejected");
        }

        if(booking.getStatus() == Booking.BookingStatus.CONFIRMED){
            Ride ride = booking.getRide();
            ride.setAvailableSeats(ride.getAvailableSeats() + booking.getSeatsBooked());
            rideRepository.save(ride);
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        Booking updated = bookingRepository.save(booking);

        return BookingResponse.from(updated);
    }

    public List<BookingResponse> getMyBookingAsRider(){
        User rider = getCurrentUser();
        return bookingRepository.findByRiderIdWithDetails(rider.getId())
                .stream()
                .map(BookingResponse::from)
                .toList();
    }

    public List<BookingResponse> getIncomingRequestAsDriver(){
        User driver = getCurrentUser();
        return bookingRepository.findByRideDriverIdWithDetails(driver.getId())
                .stream()
                .map(BookingResponse::from)
                .toList();
    }
}
