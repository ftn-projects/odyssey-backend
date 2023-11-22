package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.DiscriminatorValue;
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
@AllArgsConstructor
@DiscriminatorValue(value = "RESERVATION_CANCELLED")
public class ReservationCancelledNotification extends Notification{
    @Transient
    private static final String defaultTitle = "Reservation cancelled";
    @ManyToOne
    private Reservation reservation;

    public ReservationCancelledNotification() {
        super(null, defaultTitle, null, null);
        reservation = null;
    }

    public ReservationCancelledNotification(@NonNull Reservation reservation, @NonNull User receiver) {
        super(
                null,
                defaultTitle,
                "Reservation has been cancelled" + reservation.getGuest().getName(),
                receiver
        );
        // Additional initialization specific to AccommodationReviewedNotification if needed
        this.reservation = reservation;
    }
}
