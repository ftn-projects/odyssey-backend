package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reviews.ResponseAccommodationReviewDTO;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationReviewedNotificationDTO extends NotificationDTO {
    private ResponseAccommodationReviewDTO review;

    public AccommodationReviewedNotificationDTO(AccommodationReviewedNotif notification) {
        super(notification);
        this.review = new ResponseAccommodationReviewDTO(notification.getReview());
    }
}
