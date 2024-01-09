package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.ReviewDTOMapper;
import com.example.odyssey.services.ReviewService;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;


//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/host")
    public ResponseEntity<?> getAllHostReviews(
            @RequestParam(required = false) Long hostId,
            @RequestParam(required = false) Long submitterId,
            @RequestParam(required = false) List<Review.Status> listTypes
    ) {
        List<HostReview> reviews;
        reviews = service.getAllHostReviewsFiltered(hostId, submitterId, listTypes);
        if (reviews.isEmpty()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviews.stream().map(HostReviewDTO::new).toList(), HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accommodation")
    public ResponseEntity<?> getAllAccommodationReviews(
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) Long submitterId,
            @RequestParam(required = false) List<Review.Status> listTypes
    ) {
        List<AccommodationReview> reviews = new ArrayList<>();

        AccommodationReview review1 = new AccommodationReview();
        reviews = service.getAllAccommodationReviewsFiltered(accommodationId, submitterId, listTypes);
        if (reviews.isEmpty()) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/host/{id}")
    public ResponseEntity<?> getHostReviewById(@PathVariable Long id) {
        HostReview review;
        review = service.findHostReviewById(id);
        if (review == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getAccommodationReviewById(@PathVariable Long id) {
        AccommodationReview review;
        review = service.findAccommodationReviewById(id);
        if (review == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/host")
    public ResponseEntity<?> createHostReview(@RequestBody HostReviewDTO reviewDTO) {
        HostReview review = ReviewDTOMapper.fromDTOtoHostReview(reviewDTO);

        review = service.saveHostReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        AccommodationReview review = ReviewDTOMapper.fromDTOtoAccommodationReview(reviewDTO);

        review = service.saveAccommodationReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @PutMapping("/host/report/{id}")
    public ResponseEntity<?> reportHostReview(@PathVariable Long id, @RequestBody HostReviewDTO reviewDTO) {
        HostReview review = ReviewDTOMapper.fromDTOtoHostReview(reviewDTO);

        review = service.reportHostReview(review.getId());

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.OK);
    }

//    @PreAuthorize("hasAuthority('HOST')")
    @PutMapping("/accommodation/report/{id}")
    public ResponseEntity<?> reportAccommodationReview(@PathVariable Long id) {
        AccommodationReview review = service.reportAccommodationReview(id);
        if(review!=null)
            return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/host/{id}")
    public ResponseEntity<?> deleteHostReview(@PathVariable Long id) {
        Review review = new HostReview();

//        review = service.delete(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation/{id}")
    public ResponseEntity<?> deleteAccommodationReview(@PathVariable Long id) {
        Review review = new HostReview();

//        review = service.delete(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private static List<AccommodationReviewDTO> mapAccommodationReviewToDTO(List<AccommodationReview> users) {
        return users.stream().map(AccommodationReviewDTO::new).toList();
    }

    private static List<HostReviewDTO> mapHostReviewToDTO(List<HostReview> users) {
        return users.stream().map(HostReviewDTO::new).toList();
    }
}
