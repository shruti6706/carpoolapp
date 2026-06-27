package com.carpoolapp.backend.controller;

import com.carpoolapp.backend.dto.CreateRatingRequest;
import com.carpoolapp.backend.dto.RatingResponse;
import com.carpoolapp.backend.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    //Submit a Rating-logged-in users
    @PostMapping
    public ResponseEntity<RatingResponse> submitRating(
            @Valid @RequestBody CreateRatingRequest request
            ){
        return ResponseEntity.ok(ratingService.submitRating(request));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<List<RatingResponse>> getRatings(@PathVariable Long id){
        return ResponseEntity.ok(ratingService.getRatingsForUser(id));
    }

    @GetMapping("/users/{id}/average")
    public ResponseEntity<Map<String, Object>> getAverage(@PathVariable Long id){
        return ResponseEntity.ok(Map.of(
                "userId", id,
                "averageRating", ratingService.getAverageRating(id)
        ));


    }
}
