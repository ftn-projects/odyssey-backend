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
@DiscriminatorValue(value = "RESERVATION")
public class ReservationNotif extends Notification {

    @Transient
    private static final String defaultTitle = "Reservation Notification";

    @ManyToOne
    private Reservation reservation;

    public ReservationNotif() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationNotif(@NonNull Reservation reservation, @NonNull User receiver) {
        super(null, defaultTitle, null, receiver);
        this.reservation = reservation;

        switch (reservation.getStatus()) {
            case ACCEPTED :
                setText("Your request for " + reservation.getAccommodation().getTitle() +
                        " has been accepted");
                break;
            case DECLINED:
                setText("Your request for " + reservation.getAccommodation().getTitle() +
                        " has been declined");
                break;
            case CANCELLED_RESERVATION:
                setText("Reservation for your accommodation has been cancelled by " + reservation.getGuest().getName());
                break;
            case REQUESTED:
                setText("You have a new reservation request for " + reservation.getAccommodation().getTitle());
                break;
            case CANCELLED_REQUEST:
                setText("A reservation request for " + reservation.getAccommodation().getTitle() +
                        " has been cancelled");
                break;
            default:
                setText("You have a new reservation notification");
                break;
        }
    }
}
