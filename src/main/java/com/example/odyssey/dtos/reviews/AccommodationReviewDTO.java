package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationReviewDTO extends ReviewDTO {
    private AccommodationDTO accommodation;

    public AccommodationReviewDTO(Long id, Double rating, String comment, LocalDateTime submissionDate, UserDTO submitter, Review.Status status, AccommodationDTO accommodation) {
        super(id, rating, comment, submissionDate, submitter, status);
        this.accommodation = accommodation;
    }

    public AccommodationReviewDTO(AccommodationReview review) {
        super(review);
        accommodation = new AccommodationDTO(review.getAccommodation());
    }
}
