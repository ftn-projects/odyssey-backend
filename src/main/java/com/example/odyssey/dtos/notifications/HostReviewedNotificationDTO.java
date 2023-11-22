package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reviews.HostReviewCreationDTO;
import com.example.odyssey.entity.notifications.HostReviewedNotification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostReviewedNotificationDTO extends NotificationDTO{
    private HostReviewCreationDTO review;
    public HostReviewedNotificationDTO(HostReviewedNotification notification){
        super(notification.getId(),notification.getTitle(),notification.getText(),notification.getReceiver());
        this.review = new HostReviewCreationDTO(notification.getReview());
    }
}


