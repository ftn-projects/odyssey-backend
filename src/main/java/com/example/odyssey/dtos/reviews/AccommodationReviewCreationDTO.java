package com.example.odyssey.dtos.reviews;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationReviewCreationDTO extends ReviewCreationDTO{
    private Accommodation accommodation;

    public AccommodationReviewCreationDTO(AccommodationReview review){
        super();
        accommodation = review.getAccommodation();
    }
}
