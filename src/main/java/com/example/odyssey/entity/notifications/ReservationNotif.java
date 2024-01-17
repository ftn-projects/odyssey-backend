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

    public ReservationNotif(@NonNull Reservation reservation, @NonNull User receiver) {
        super(-1L, "", "", LocalDateTime.now(), false, null, receiver);
        Notification.Type type = switch (reservation.getStatus()) {
            case REQUESTED -> Type.RESERVATION_MADE;
            case ACCEPTED -> Type.RESERVATION_ACCEPTED;
            case DECLINED -> Type.RESERVATION_DECLINED;
            default -> throw new IllegalStateException("Unexpected value: " + reservation.getStatus());
        };
        setType(type);
        this.reservation = reservation;
    }
}
