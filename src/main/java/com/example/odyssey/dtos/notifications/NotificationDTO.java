package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.notifications.AccommodationReviewedNotif;
import com.example.odyssey.entity.notifications.HostReviewedNotif;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.notifications.ReservationNotif;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String text;
    private UserDTO receiver;
    private ReservationDTO reservation;
    private AccommodationReviewDTO accommodationReview;
    private HostReviewDTO hostReview;

    public NotificationDTO(Long id, String title, String text, User receiver) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.receiver = new UserDTO(receiver);
    }

    public NotificationDTO(Notification notification) {
        this(notification.getId(), notification.getTitle(), notification.getText(), notification.getReceiver());
    }

    public NotificationDTO(AccommodationReviewedNotif notification) {
        this(notification.getId(), notification.getTitle(), notification.getText(), notification.getReceiver());
        accommodationReview = new AccommodationReviewDTO(notification.getReview());
    }

    public NotificationDTO(HostReviewedNotif notification) {
        this(notification.getId(), notification.getTitle(), notification.getText(), notification.getReceiver());
        hostReview = new HostReviewDTO(notification.getReview());
    }

    public NotificationDTO(ReservationNotif notification) {
        this(notification.getId(), notification.getTitle(), notification.getText(), notification.getReceiver());
        reservation = new ReservationDTO(notification.getReservation());
    }
}
