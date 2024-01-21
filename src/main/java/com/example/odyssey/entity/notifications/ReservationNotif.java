package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "RESERVATION")
public class ReservationNotif extends Notification {

    @Transient
    private static final String defaultTitle = "Reservation Notification";

    @ManyToOne
    private Reservation reservation;

    public ReservationNotif(@NonNull Reservation reservation, @NonNull User receiver, Notification.Type type, String title) {
        super(null, title, "", null, null, type, receiver);
        this.reservation = reservation;
    }
}
