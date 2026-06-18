package com.carpoolapp.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    @JsonIgnore
    private User driver;

    @NotBlank
    private String source;

    @NotBlank
    private String destination;

    private LocalDateTime departureTime;

    @Positive
    private int availableSeats;

    private Double pricePerSeat;

    private boolean womenOnly = false;

    @Enumerated(EnumType.STRING)
    private RideStatus status = RideStatus.SCHEDULED;

    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    public enum RideStatus {
        SCHEDULED, ONGOING, COMPLETED, CANCELLED
    }

}
