package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reviews.ResponseAccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.ResponseHostReviewDTO;
import com.example.odyssey.dtos.reviews.ResponseReviewDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/v1/reviews")
public class ReviewController {
    @GetMapping("/{id}")
    public HostReview getHostReview(@PathVariable Long id){
        return new HostReview(id,5.0,"bravo", Review.Status.ACCEPTED,LocalDateTime.now(),new Guest(),new Host());
    }

    @GetMapping("/{id}")
    public AccommodationReview getAccommodationReview(@PathVariable Long id){
        return new AccommodationReview(id,5.0,"bravo", Review.Status.ACCEPTED,LocalDateTime.now(),new Guest(),new Accommodation());
    }

    @PostMapping
    public ResponseEntity<ResponseHostReviewDTO> postHostReview (@RequestBody ResponseHostReviewDTO dto){
        HostReview review = new HostReview();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setSubmissionDate(dto.getSubmissionDate());
        review.setSubmitter(new Guest());
        review.setHost(new Host());

        return new ResponseEntity<>(new ResponseHostReviewDTO(review), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<ResponseAccommodationReviewDTO> postAccommodationReview(@RequestBody ResponseAccommodationReviewDTO dto){
        AccommodationReview review = new AccommodationReview();
        review.setId(dto.getId());
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setSubmissionDate(dto.getSubmissionDate());
        review.setSubmitter(new Guest());
        review.setAccommodation(new Accommodation());

        return new ResponseEntity<>(new ResponseAccommodationReviewDTO(review), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteHostReview(@PathVariable Long id){
        HostReview review = new HostReview();
        review.setId(id);
        review.setStatus(Review.Status.DECLINED);
        if(id != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodationReview(@PathVariable Long id){
        AccommodationReview review = new AccommodationReview();
        review.setId(id);
        review.setStatus(Review.Status.DECLINED);
        if(id != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
