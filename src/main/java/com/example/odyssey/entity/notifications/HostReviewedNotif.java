package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@DiscriminatorValue(value = "HOST_REVIEW")
public class HostReviewedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Host page reviewed";
    @ManyToOne
    @JoinColumn(name = "host_review_id", referencedColumnName = "id")
    private HostReview review;

    public HostReviewedNotif() {
        super(null, defaultTitle, null, null);
        review = null;
    }

    public HostReviewedNotif(@NonNull HostReview review, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Your host page has been reviewed by " + review.getSubmitter().getName(),
                receiver
        );
        this.review = review;
    }
}
