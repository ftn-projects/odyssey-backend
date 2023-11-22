package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue(value = "RESERVATION_ACCREDITED")
public class ReservationAccreditedNotif extends Notification {
    @Transient
    private static final String defaultTitle = "Reservation accredited";
    @ManyToOne
    private Reservation reservation;

    public ReservationAccreditedNotif() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationAccreditedNotif(@NonNull Reservation reservation, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Your request for " + reservation.getAccommodation().getTitle() + " has been " + reservation.getStatus().toString().toLowerCase(),
                receiver
        );
        this.reservation = reservation;
    }
}
