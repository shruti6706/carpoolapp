package com.carpoolapp.backend.repository;

import com.carpoolapp.backend.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {


    //to prevent duplicate rating on same booking
    Optional<Rating> findByRaterIdAndBookingId(Long raterId, Long bookingId);

    //all ratings received by user
    @Query("""
    SELECT r FROM Rating r
    JOIN FETCH r.rater
    JOIN FETCH r.ratee
    JOIN FETCH r.booking b
    JOIN FETCH b.ride
    WHERE r.ratee.id = :userId
    ORDER BY r.createdAt DESC
    """)
    List<Rating> findByRateeId(@Param("userId") Long userId);

    //avg stars
    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.ratee.id = :userId")
    Double findAverageRatingByUserId(@Param("userId") Long userId);


}
