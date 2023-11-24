package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAccommodationReviewDTO extends ResponseReviewDTO {
    private AccommodationDTO accommodation;

    public ResponseAccommodationReviewDTO(AccommodationReview review) {
        super(review);
        accommodation = new AccommodationDTO(review.getAccommodation());
    }
}
