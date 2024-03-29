package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotif;
import com.example.odyssey.entity.notifications.HostReviewedNotif;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.mappers.ReviewDTOMapper;
import com.example.odyssey.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AccommodationService accommodationService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<Review.Status> statuses,
            @RequestParam(required = false) List<String> types) {
        List<Review> reviews = service.getAllFiltered(search, statuses, types);
        return new ResponseEntity<>(reviews.stream().map(this::mapReviewToDTO).toList(), HttpStatus.OK);
    }

    @GetMapping("/host")
    public ResponseEntity<?> getAllHostReviews(
            @RequestParam(required = false) Long hostId,
            @RequestParam(required = false) Long submitterId,
            @RequestParam(required = false) List<Review.Status> listTypes
    ) {
        List<HostReview> reviews = service
                .getAllHostReviewsFiltered(hostId, submitterId, listTypes)
                .stream().filter(r ->
                        !r.getStatus().equals(Review.Status.REQUESTED) && !r.getStatus().equals(Review.Status.DECLINED))
                .toList();
        return new ResponseEntity<>(reviews.stream().map(HostReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/accommodation")
    public ResponseEntity<?> getAllAccommodationReviews(
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) Long submitterId,
            @RequestParam(required = false) List<Review.Status> listTypes
    ) {
        List<AccommodationReview> reviews = service
                .getAllAccommodationReviewsFiltered(accommodationId, submitterId, listTypes)
                .stream().filter(r ->
                        !r.getStatus().equals(Review.Status.REQUESTED) && !r.getStatus().equals(Review.Status.DECLINED))
                .toList();
        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/accommodation/host/{id}")
    public ResponseEntity<?> getAllAccommodationReviewsByHost(
            @PathVariable Long id,
            @RequestParam(required = false) List<Review.Status> listTypes
    ) {
        List<AccommodationReview> reviews = service
                .getAllAccommodationReviewsByHost(id, listTypes);
        if (listTypes == null) System.out.println("List types is null");
        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/host/{id}")
    public ResponseEntity<?> getHostReviewById(@PathVariable Long id) {
        HostReview review;
        review = service.findHostReviewById(id);
        if (review == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getAccommodationReviewById(@PathVariable Long id) {
        AccommodationReview review;
        review = service.findAccommodationReviewById(id);
        if (review == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
    }

    @GetMapping("/accommodation/rating/{id}")
    public ResponseEntity<?> getAccommodationRatings(@PathVariable Long id) {
        List<Integer> ratings = service.getRatingsByAccommodation(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

    @GetMapping("/host/rating/{id}")
    public ResponseEntity<?> getHostRatings(@PathVariable Long id) {
        List<Integer> ratings = service.getRatingsByHost(id);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }


    @PostMapping("/host")
    public ResponseEntity<?> createHostReview(@RequestBody HostReviewDTO dto) {
        HostReview review = service.saveHostReview(
                ReviewDTOMapper.fromDTOtoHostReview(dto));
        notificationService.create(new HostReviewedNotif(review, review.getHost()));
        notificationService.create(new HostReviewedNotif(review, userService.getAdmin()));

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.CREATED);
    }


    @PostMapping("/accommodation")
    public ResponseEntity<?> createAccommodationReview(@RequestBody AccommodationReviewDTO dto) {
        AccommodationReview review = ReviewDTOMapper.fromDTOtoAccommodationReview(dto);
        review.setSubmitter((Guest) userService.findById(dto.getSubmitter().getId()));
        review.setAccommodation(accommodationService.findById(dto.getAccommodation().getId()));
        review = service.saveAccommodationReview(review);

        notificationService.create(new AccommodationReviewedNotif(review, review.getAccommodation().getHost()));
        notificationService.create(new AccommodationReviewedNotif(review, userService.getAdmin()));

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
        if (review != null)
            return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/host/{id}")
    public ResponseEntity<?> deleteHostReview(@PathVariable Long id) {
        Review review = new HostReview();

        service.deleteHostReview(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation/{id}")
    public ResponseEntity<?> deleteAccommodationReview(@PathVariable Long id) {
        service.deleteAccommodationReview(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/accept/{id}")
    public ResponseEntity<?> accept(@PathVariable Long id) {
        service.accept(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/decline/{id}")
    public ResponseEntity<?> decline(@PathVariable Long id) {
        service.decline(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/dismiss/{id}")
    public ResponseEntity<?> dismiss(@PathVariable Long id) {
        service.dismiss(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static List<AccommodationReviewDTO> mapAccommodationReviewToDTO(List<AccommodationReview> users) {
        return users.stream().map(AccommodationReviewDTO::new).toList();
    }

    private static List<HostReviewDTO> mapHostReviewToDTO(List<HostReview> users) {
        return users.stream().map(HostReviewDTO::new).toList();
    }

    private ReviewDTO mapReviewToDTO(Review review) {
        if (review instanceof AccommodationReview)
            return new AccommodationReviewDTO((AccommodationReview) review);
        return new HostReviewDTO((HostReview) review);
    }
}
