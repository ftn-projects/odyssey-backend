package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.accommodations.ResponseAccommodationBaseDTO;
import com.example.odyssey.dtos.accommodations.ResponseAccommodationSummaryDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAccommodationReviewDTO extends ResponseReviewDTO {
    private ResponseAccommodationBaseDTO accommodation;

    public ResponseAccommodationReviewDTO(AccommodationReview review) {
        super(review);
        accommodation = new ResponseAccommodationBaseDTO(review.getAccommodation());
    }
}
