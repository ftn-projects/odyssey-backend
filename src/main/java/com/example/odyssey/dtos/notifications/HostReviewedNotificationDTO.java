package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reviews.RequestHostReviewDTO;
import com.example.odyssey.dtos.reviews.ResponseHostReviewDTO;
import com.example.odyssey.entity.notifications.HostReviewedNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HostReviewedNotificationDTO extends NotificationDTO {
    private ResponseHostReviewDTO review;

    public HostReviewedNotificationDTO(HostReviewedNotif notification) {
        super(notification);
        this.review = new ResponseHostReviewDTO(notification.getReview());
    }
}


