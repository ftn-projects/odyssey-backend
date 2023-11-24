package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorColumn(name = "NOTIF_TYPE")
public class ReservationNotif extends Notification {

    @Transient
    private static final String defaultTitle = "Reservation Notification";

    @Enumerated(value = EnumType.ORDINAL)
    NotificationType type;

    @ManyToOne
    private Reservation reservation;


    public enum NotificationType {
        RESERVATION_ACCREDITED,
        RESERVATION_CANCELLED,
        RESERVATION_REQUESTED
        // Add more notification types as needed
    }

    public ReservationNotif() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationNotif(@NonNull NotificationType notificationType, @NonNull Reservation reservation, @NonNull User receiver) {
        super(null, defaultTitle, null, receiver);
        this.reservation = reservation;

        switch (notificationType) {
            case RESERVATION_ACCREDITED:
                setText("Your request for " + reservation.getAccommodation().getTitle() +
                        " has been " + reservation.getStatus().toString().toLowerCase());
                break;
            case RESERVATION_CANCELLED:
                setText("Reservation has been cancelled" + reservation.getGuest().getName());
                break;
            case RESERVATION_REQUESTED:
                setText("Your request has been " + reservation.getStatus().toString().toLowerCase());
                break;
            // Add more cases for additional notification types
            default:
                // Handle unknown type or throw an exception
                break;
        }
    }
}
