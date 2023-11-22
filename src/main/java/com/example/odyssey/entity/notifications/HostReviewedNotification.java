package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue(value = "HOST_REVIEW")
public class HostReviewedNotification extends Notification {
    @Transient
    private static final String defaultTitle = "Host page reviewed";
    @ManyToOne
    private HostReview review;

    public HostReviewedNotification() {
        super(null, defaultTitle, null, null);
        review = null;
    }

    public HostReviewedNotification(@NonNull HostReview review, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Your host page has been reviewed by " + review.getSubmitter().getName(),
                receiver
        );
        this.review = review;
    }
}
