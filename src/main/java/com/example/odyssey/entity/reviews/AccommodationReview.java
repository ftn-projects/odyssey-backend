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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accommodation_reviews")
public class AccommodationReview extends Review {
    @ManyToOne
    private Accommodation accommodation;

    public AccommodationReview(Long id, Double rating, String comment, Status status, LocalDateTime submissionDate, Guest submitter, Accommodation accommodation) {
        super(id, rating, comment, status, submissionDate, submitter);
        this.accommodation = accommodation;
    }
}
