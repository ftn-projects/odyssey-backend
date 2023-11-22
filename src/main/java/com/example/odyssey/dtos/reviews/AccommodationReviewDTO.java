package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.entity.reviews.AccommodationReview;

public class AccommodationReviewDTO extends ReviewDTO{
    private AccommodationDTO accommodation;

    public AccommodationReviewDTO(AccommodationReview review){
        super(review);
        accommodation = new AccommodationDTO(review.getAccommodation());
    }
}
