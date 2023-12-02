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
        super(null, null);
        reservation = null;
    }

    public ReservationNotif(@NonNull Reservation reservation, @NonNull User receiver) {
        super(null, receiver);
        this.reservation = reservation;
    }
}
