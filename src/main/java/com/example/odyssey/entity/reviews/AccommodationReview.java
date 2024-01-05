package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "accommodation_reviews")
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
