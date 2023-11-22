package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reviews.HostReview;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostReviewedNotification extends  Notification{
    @ManyToOne
    HostReview review;
}
