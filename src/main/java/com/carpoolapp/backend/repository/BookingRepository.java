package com.carpoolapp.backend.repository;

import com.carpoolapp.backend.entity.Booking;
import com.carpoolapp.backend.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT r FROM Ride r WHERE r.id = :id")
    Optional<Ride> findByIdForUpdate(@Param("id") Long id);

    @Query("SELECT b FROM Booking b JOIN FETCH b.rider JOIN FETCH b.ride r JOIN FETCH r.driver WHERE b.id = :id")
    Optional<Booking> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT b FROM Booking b WHERE b.rider.id = :riderId AND b.ride.id = :rideId AND b.status IN ('PENDING', 'CONFIRMED')")
    Optional<Booking> findActiveByRiderAndRide(@Param("riderId") Long riderId, @Param("rideId") Long rideId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.rider JOIN FETCH b.ride r JOIN FETCH r.driver WHERE b.rider.id = :riderId ORDER BY b.createdAt DESC")
    List<Booking> findByRiderIdWithDetails(@Param("riderId") Long riderId);

    @Query("SELECT b FROM Booking b JOIN FETCH b.rider JOIN FETCH b.ride r JOIN FETCH r.driver WHERE r.driver.id = :driverId ORDER BY b.createdAt DESC")
    List<Booking> findByRideDriverIdWithDetails(@Param("driverId") Long driverId);
}
