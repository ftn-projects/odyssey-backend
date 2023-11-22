package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
public class AccommodationReviewedNotification extends Notification {
    @Transient
    private static final String defaultTitle = "Accommodation reviewed";

    public AccommodationReviewedNotification() {
        super(null, defaultTitle, null, null);
        review = null;
    }

    public AccommodationReviewedNotification(@NonNull AccommodationReview review, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Accommodation "+ review.getAccommodation().getTitle() + " has been reviewed by" + review.getGuest().getName(),
                receiver
                );

        // Additional initialization specific to AccommodationReviewedNotification if needed
        this.review = review;
    }


    @ManyToOne
    AccommodationReview review;
}
