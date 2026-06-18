package com.carpoolapp.backend.repository;

import com.carpoolapp.backend.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RideRepository extends JpaRepository<Ride, Long> {

    @Query("SELECT r FROM Ride r JOIN FETCH r.driver WHERE r.id = :id")
    Optional<Ride> findByIdWithDriver(@Param("id") Long id);

    @Query("SELECT r FROM Ride r JOIN FETCH r.driver ORDER BY r.departureTime ASC")
    List<Ride> findAllWithDriver();

    @Query("SELECT r FROM Ride r JOIN FETCH r.driver WHERE r.driver.id = :driverId")
    List<Ride> findByDriverIdWithDriver(@Param("driverId") Long driverId);

    @Query("SELECT r FROM Ride r JOIN FETCH r.driver " +
            "WHERE LOWER(r.source) LIKE LOWER(CONCAT('%', :source, '%')) " +
            "AND LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%')) " +
            "AND r.availableSeats > 0 " +
            "AND r.status = 'SCHEDULED'")
    List<Ride> searchRides(@Param("source") String source, @Param("destination") String destination);

    @Query("SELECT r FROM Ride r JOIN FETCH r.driver WHERE " +
            "LOWER(r.source) LIKE LOWER(CONCAT('%', :source, '%')) AND " +
            "LOWER(r.destination) LIKE LOWER(CONCAT('%', :destination, '%')) AND " +
            "r.status = 'SCHEDULED' AND r.availableSeats > 0 AND " +
            "r.womenOnly = :womenOnly")
    List<Ride> searchRidesWithFilter(@Param("source") String source,
                                     @Param("destination") String destination,
                                     @Param("womenOnly") boolean womenOnly);
}