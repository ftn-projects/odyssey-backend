package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reports.ReportDTO;
import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reports.Report;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/reviews")
public class ReviewController {
//    @Autowired
//    private ReviewService service;
//
//    @Autowired
//    public ReviewController(ReviewService service) {
//        this.service = service;
//    }

    private final List<HostReview> dataHost;
    private final List<AccommodationReview> dataAccommodation;

    public ReviewController() {
        dataHost = new ArrayList<>() {{
            add(new HostReview());
            add(new HostReview());
        }};
        dataAccommodation = new ArrayList<>() {{
            add(new AccommodationReview());
            add(new AccommodationReview());
        }};
    }

    @GetMapping("/host")
    public ResponseEntity<List<HostReviewDTO>> getAllHostReviews() {
        List<HostReview> reviews = dataHost;

//        reviews = service.getAllHostReviews(id);
        // TODO return new AccommodationReview(id, 5.0, "bravo", Review.Status.ACCEPTED, LocalDateTime.now(), new Guest(), new Accommodation());
        return new ResponseEntity<>(reviews.stream().map(HostReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/accommodation")
    public ResponseEntity<List<AccommodationReviewDTO>> getAllAccommodationReviews() {
        List<AccommodationReview> reviews = dataAccommodation;

//        reviews = service.getAllAccommodationReviews(id);
        // TODO return new AccommodationReview(id, 5.0, "bravo", Review.Status.ACCEPTED, LocalDateTime.now(), new Guest(), new Accommodation());
        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<HostReviewDTO> getHostReviewById(@PathVariable Long id) {
        HostReview review = dataHost.stream().filter((r) -> Objects.equals(r.getId(), id))
                .findFirst().orElse(new HostReview());

//        review = service.findHostReviewById(id);
        // TODO new HostReview(id, 5.0, "bravo", Review.Status.ACCEPTED, LocalDateTime.now(), new Guest(), new Host());
        return new ResponseEntity<>(new HostReviewDTO(review), HttpStatus.OK);
    }

    @GetMapping("/accommodation/{id}")
    public ResponseEntity<AccommodationReviewDTO> getAccommodationReviewById(@PathVariable Long id) {
        AccommodationReview review = dataAccommodation.stream().filter((r) -> Objects.equals(r.getId(), id))
                .findFirst().orElse(new AccommodationReview());

//        review = service.findAccommodationReviewById(id);
        // TODO return new AccommodationReview(id, 5.0, "bravo", Review.Status.ACCEPTED, LocalDateTime.now(), new Guest(), new Accommodation());
        return new ResponseEntity<>(new AccommodationReviewDTO(review), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HostReviewDTO> createHostReview(@RequestBody HostReviewDTO reviewDTO) {
        HostReview review = new HostReview();

//        review = service.createHostReview(review);

        return new ResponseEntity<>(new HostReviewDTO(review), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AccommodationReviewDTO> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        AccommodationReview review = new AccommodationReview();

//        review = service.createAccommodationReview(review);

        return new ResponseEntity<>(new AccommodationReviewDTO(review), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> delete(@PathVariable Long id) {
        Review review = new HostReview();

//        review = service.delete(id);

        ReviewDTO reviewDTO;
        if (review instanceof HostReview)
            reviewDTO = new HostReviewDTO((HostReview) review);
        else reviewDTO = new AccommodationReviewDTO((AccommodationReview) review);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }
}
