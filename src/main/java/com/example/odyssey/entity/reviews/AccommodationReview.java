package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DiscriminatorValue("AR")
public class AccommodationReview extends Review {
    @ManyToOne
    private Accommodation accommodation;

    public AccommodationReview(Double rating, String comment, Status status, LocalDateTime submissionDate, Guest submitter, Accommodation reviewedAccommodation) {
        super(rating, comment, status, submissionDate, submitter);
        this.accommodation = reviewedAccommodation;
    }

    public AccommodationReview() {

    }
}
