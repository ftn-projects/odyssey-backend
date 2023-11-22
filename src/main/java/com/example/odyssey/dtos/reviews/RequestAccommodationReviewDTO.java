package com.example.odyssey.dtos.reviews;

import com.example.odyssey.entity.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestAccommodationReviewDTO extends RequestReviewDTO {
    private Long accommodationId;

    public RequestAccommodationReviewDTO(Double rating, String comment, Review.Status status, LocalDateTime submissionDate, Long submitterId, Long accommodationId) {
        super(rating, comment, status, submissionDate, submitterId);
        this.accommodationId = accommodationId;
    }
}
