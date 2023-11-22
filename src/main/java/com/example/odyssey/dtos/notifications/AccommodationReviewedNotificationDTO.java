package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationReviewedNotificationDTO extends NotificationDTO{
    private AccommodationReviewDTO review;
    public AccommodationReviewedNotificationDTO(AccommodationReviewedNotification notification){
        super(notification.getId(),notification.getTitle(),notification.getText(),notification.getReceiver());
        this.review = new AccommodationReviewDTO(notification.getReview());
    }
}
