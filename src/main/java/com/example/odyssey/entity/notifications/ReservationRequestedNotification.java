package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestedNotification extends Notification{
    @Transient
    private static final String defaultTitle = "Reservation accredited";

    public ReservationRequestedNotification() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationRequestedNotification(@NonNull Reservation reservation, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Your request has been " + reservation.getStatus().toString().toLowerCase(),
                receiver
        );

        this.reservation = reservation;
    }


    @ManyToOne
    Reservation reservation;
}
