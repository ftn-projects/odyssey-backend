package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Entity
@Getter
@Setter
@Validated
@DiscriminatorValue(value = "ACCOMMODATION_REVIEW")
public class AccommodationReviewedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Accommodation reviewed";
    @ManyToOne
    @JoinColumn(name = "accommodation_review_id", referencedColumnName = "id")
    private AccommodationReview review;

    public AccommodationReviewedNotif() {
        super(null, null);
        review = null;
    }


    public AccommodationReviewedNotif(@NonNull AccommodationReview review, @NonNull User receiver) {
        super(
                null, receiver
        );
        // Additional initialization specific to AccommodationReviewedNotification if needed
        this.review = review;
    }


}
