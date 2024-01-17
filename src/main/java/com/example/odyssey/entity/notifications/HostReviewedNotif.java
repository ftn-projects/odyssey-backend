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
    @Transient
    private static final String defaultTitle = "Host page reviewed";
    @ManyToOne
    @JoinColumn(name = "host_review_id", referencedColumnName = "id")
    private HostReview review;

    public HostReviewedNotif(@NonNull HostReview review, @NonNull User receiver) {
        super(-1L, "", "", LocalDateTime.now(), false, Type.HOST_REVIEW, receiver);
        this.review = review;
    }
}
