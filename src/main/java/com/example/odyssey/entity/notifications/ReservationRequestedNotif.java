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
@DiscriminatorValue(value = "RESERVATION_REQUESTED")
public class ReservationRequestedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Reservation accredited";
    @ManyToOne
    private Reservation reservation;

    public ReservationRequestedNotif() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationRequestedNotif(@NonNull Reservation reservation, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Your request has been " + reservation.getStatus().toString().toLowerCase(),
                receiver
        );
        this.reservation = reservation;
    }
}
