package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import com.example.odyssey.dtos.reviews.ResponseAccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.ResponseHostReviewDTO;
import com.example.odyssey.dtos.users.ResponseUserDTO;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotif;
import com.example.odyssey.entity.notifications.HostReviewedNotif;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.notifications.ReservationNotif;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String text;
    private ResponseUserDTO receiver;
    private ResponseReservationDTO reservation;
    private ResponseAccommodationReviewDTO accommodationReview;
    private ResponseHostReviewDTO hostReview;


    public NotificationDTO(Notification notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.receiver = new ResponseUserDTO(notification.getReceiver());
    }

    public NotificationDTO(AccommodationReviewedNotif notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.receiver = new ResponseUserDTO(notification.getReceiver());
        this.accommodationReview = new ResponseAccommodationReviewDTO(notification.getReview());
    }

    public NotificationDTO(HostReviewedNotif notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.receiver = new ResponseUserDTO(notification.getReceiver());
        this.hostReview = new ResponseHostReviewDTO(notification.getReview());
    }

    public NotificationDTO(ReservationNotif notification){
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.text = notification.getText();
        this.receiver = new ResponseUserDTO(notification.getReceiver());
        this.reservation = new ResponseReservationDTO(notification.getReservation());
    }


}
