package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.reservations.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelledNotification extends Notification{
    @ManyToOne
    Reservation reservation;
}
