package com.example.odyssey.dtos.notifications;

import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.dtos.reservation.ReservationsAccreditDTO;
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

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String title;
    private String text;
    private LocalDateTime date;
    private Boolean read;
    private UserDTO receiver;
    private ReservationsAccreditDTO reservation;
    private AccommodationReviewDTO accommodationReview;
    private HostReviewDTO hostReview;

    public NotificationDTO(Notification notification) {
        id = notification.getId();
        title = notification.getTitle();
        text = notification.getDescription();
        date = notification.getDate();
        read = notification.getRead();
        receiver = new UserDTO(notification.getReceiver());

        if (notification instanceof AccommodationReviewedNotif)
            accommodationReview = new AccommodationReviewDTO(((AccommodationReviewedNotif) notification).getReview());
        else if (notification instanceof HostReviewedNotif)
            hostReview = new HostReviewDTO(((HostReviewedNotif) notification).getReview());
        else if (notification instanceof ReservationNotif)
            reservation = new ReservationsAccreditDTO(((ReservationNotif) notification).getReservation(), -1);
    }
}
