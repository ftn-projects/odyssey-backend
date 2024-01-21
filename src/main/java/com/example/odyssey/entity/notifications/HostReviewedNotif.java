package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "HOST_REVIEW")
public class HostReviewedNotif extends Notification {
    @ManyToOne
    @JoinColumn(name = "host_review_id", referencedColumnName = "id")
    private HostReview review;

    public HostReviewedNotif(@NonNull HostReview review, User receiver) {
        super(null, "", "", null, null, Type.HOST_REVIEW, receiver);
        this.review = review;

        String submitter = review.getSubmitter().getName() + " " + review.getSubmitter().getSurname();
        String host = review.getHost().getName() + " " + review.getHost().getSurname();

        boolean isAdmin = !receiver.getId().equals(review.getHost().getId());
        this.title = submitter + " reviewed " + (isAdmin ? host : "your profile page");
    }
}
