package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.mappers.ReviewDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final List<HostReview> hostData = DummyData.getHostReviews();
    private final List<AccommodationReview> accommodationData = DummyData.getAccommodationReviews();

    @GetMapping("/host")
    public ResponseEntity<List<HostReviewDTO>> getAllHostReviews() {
        List<HostReview> reviews = hostData;

//        reviews = service.getAllHostReviews(id);

        return new ResponseEntity<>(reviews.stream().map(HostReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/accommodation")
    public ResponseEntity<List<AccommodationReviewDTO>> getAllAccommodationReviews() {
        List<AccommodationReview> reviews = accommodationData;

//        reviews = service.getAllAccommodationReviews(id);

        return new ResponseEntity<>(reviews.stream().map(AccommodationReviewDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<HostReviewDTO> getHostReviewById(@PathVariable Long id) {
        HostReview review = hostData.stream().filter((r) -> Objects.equals(r.getId(), id))
                .findFirst().orElse(new HostReview());

//        review = service.findHostReviewById(id);

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.OK);
    }

    @GetMapping("/accommodation/{id}")
    public ResponseEntity<AccommodationReviewDTO> getAccommodationReviewById(@PathVariable Long id) {
        AccommodationReview review = accommodationData.stream().filter((r) -> Objects.equals(r.getId(), id))
                .findFirst().orElse(new AccommodationReview());

//        review = service.findAccommodationReviewById(id);

        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HostReviewDTO> createHostReview(@RequestBody HostReviewDTO reviewDTO) {
        HostReview review = ReviewDTOMapper.fromDTOtoHostReview(reviewDTO);

//        review = service.createHostReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromHostReviewToDTO(review), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<AccommodationReviewDTO> createAccommodationReview(@RequestBody AccommodationReviewDTO reviewDTO) {
        AccommodationReview review = ReviewDTOMapper.fromDTOtoAccommodationReview(reviewDTO);

//        review = service.createAccommodationReview(review);

        return new ResponseEntity<>(ReviewDTOMapper.fromAccommodationReviewToDTO(review), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> delete(@PathVariable Long id) {
        Review review = new HostReview();

//        review = service.delete(id);

        return new ResponseEntity<>(ReviewDTOMapper.fromReviewToDTO(review), HttpStatus.OK);
    }
}
