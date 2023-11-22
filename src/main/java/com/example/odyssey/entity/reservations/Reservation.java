package com.example.odyssey.entity.reservations;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    Double price;
    Integer guestNumber;
    ReservationStatus status;
    LocalDateTime requestDate;
    LocalDateTime reservationDate;
    @Embedded
    TimeSlot timeSlot;
    @ManyToOne
    Accommodation accommodation;
    @ManyToOne
    Guest guest;
    public enum ReservationStatus {
        REQUESTED,
        DECLINED,
        CANCELLED_REQUEST,
        CANCELLED_RESERVATION,
        ACCEPTED
    }
}