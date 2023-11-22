package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@AllArgsConstructor
@DiscriminatorValue(value = "ACCOMMODATION_REVIEW")
public class AccommodationReviewedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Accommodation reviewed";
    @ManyToOne
    private AccommodationReview review;

    public AccommodationReviewedNotif() {
        super(null, defaultTitle, null, null);
        review = null;
    }

    public AccommodationReviewedNotif(@NonNull AccommodationReview review, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Accommodation " + review.getAccommodation().getTitle() + " has been reviewed by" + review.getSubmitter().getName(),
                receiver
        );
        // Additional initialization specific to AccommodationReviewedNotification if needed
        this.review = review;
    }
}
