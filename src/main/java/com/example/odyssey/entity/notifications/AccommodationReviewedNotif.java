package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Validated
@NoArgsConstructor
@DiscriminatorValue(value = "ACCOMMODATION_REVIEW")
public class AccommodationReviewedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Accommodation reviewed";
    @ManyToOne
    @JoinColumn(name = "accommodation_review_id", referencedColumnName = "id")
    private AccommodationReview review;

    public AccommodationReviewedNotif(@NonNull AccommodationReview review, @NonNull User receiver) {
        super(null, "", "", null, null, Type.ACCOMMODATION_REVIEW, receiver);
        this.review = review;

        String submitter = review.getSubmitter().getName() + " " + review.getSubmitter().getSurname();
        String accommodation = review.getAccommodation().getTitle();

        boolean isAdmin = !receiver.getId().equals(review.getAccommodation().getHost().getId());
        this.title = submitter + " reviewed " + (isAdmin ? accommodation : "your accommodation " + accommodation);
    }
}
