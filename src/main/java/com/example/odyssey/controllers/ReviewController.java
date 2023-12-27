package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.mappers.ReviewDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reviews")
public class ReviewController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/host")
    public ResponseEntity<?> getAllHostReviews() {
        List<HostReview> reviews = new ArrayList<>();
//        reviews = service.getAllHostReviews(id);
        if (reviews.isEmpty()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviews.stream().map(HostReviewDTO::new).toList(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accommodation")
    public ResponseEntity<?> getAllAccommodationReviews() {
        List<AccommodationReview> reviews = new ArrayList<>();

//        reviews = service.getAllAccommodationReviews(id);
        if (reviews.isEmpty()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/host/{id}")
    public ResponseEntity<?> getHostReviewById(@PathVariable Long id) {
        HostReview review = new HostReview();

//        review = service.findHostReviewById(id);
        if (review == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getAccommodationReviewById(@PathVariable Long id) {
        AccommodationReview review =new AccommodationReview();

//        review = service.findAccommodationReviewById(id);
        if (review == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/host")
    public ResponseEntity<?> createHostReview(@RequestBody HostReviewDTO reviewDTO) {
        HostReview review = ReviewDTOMapper.fromDTOtoHostReview(reviewDTO);

//        review = service.createHostReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        AccommodationReview review = ReviewDTOMapper.fromDTOtoAccommodationReview(reviewDTO);

//        review = service.createAccommodationReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Review review = new HostReview();

//        review = service.delete(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
