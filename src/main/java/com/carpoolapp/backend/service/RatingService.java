package com.carpoolapp.backend.service;

import com.carpoolapp.backend.dto.CreateRatingRequest;
import com.carpoolapp.backend.dto.RatingResponse;
import com.carpoolapp.backend.entity.Booking;
import com.carpoolapp.backend.entity.Rating;
import com.carpoolapp.backend.entity.Ride;
import com.carpoolapp.backend.entity.User;
import com.carpoolapp.backend.repository.BookingRepository;
import com.carpoolapp.backend.repository.RatingRepository;
import com.carpoolapp.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    @Transactional
    public RatingResponse submitRating(CreateRatingRequest request){
        User rater = getCurrentUser();

        Booking booking = bookingRepository.findByIdWithDetails(request.getBookingId())
                .orElseThrow(()-> new RuntimeException("Booking not found"));

        if(booking.getRide().getStatus() != Ride.RideStatus.COMPLETED){
            throw new RuntimeException("You can only rate after the ride is completed");
        }

        boolean isRider = booking.getRider().getId().equals(rater.getId());
        boolean isDriver = booking.getRide().getDriver().getId().equals(rater.getId());

        if(!isDriver && !isRider){
            throw new RuntimeException("You were not part of this booking");
        }

        //Duplicate prevention
        ratingRepository.findByRaterIdAndBookingId(rater.getId(), booking.getId())
                .ifPresent(r->{
                    throw new RuntimeException("You have already rated this booking");
                });

        //Derive ratee automatically
        User ratee = isRider
                ? booking.getRide().getDriver():booking.getRider();

        Rating rating = new Rating();
        rating.setRater(rater);
        rating.setRatee(ratee);
        rating.setBooking(booking);
        rating.setStars(request.getStars());
        rating.setComment(request.getComment());

        return RatingResponse.from(ratingRepository.save(rating));

    }

    //All logged-in users can see user's ratings
    public List<RatingResponse> getRatingsForUser(Long userId){
        return ratingRepository.findByRateeId(userId)
                .stream()
                .map(RatingResponse::from)
                .toList();
    }

    public Double getAverageRating(Long userId){
        Double avg = ratingRepository.findAverageRatingByUserId(userId);
        return avg !=null?Math.round(avg*10.00)/10.0 : 0.0;
    }
}
