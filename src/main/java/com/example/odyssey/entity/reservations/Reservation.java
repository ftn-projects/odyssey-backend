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
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private Integer guestNumber;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
    private LocalDateTime requestDate;
    private LocalDateTime reservationDate;
    @Embedded
    private TimeSlot timeSlot;
    @ManyToOne
    private Accommodation accommodation;
    @ManyToOne
    private Guest guest;

    public enum Status {REQUESTED, DECLINED, CANCELLED_REQUEST, CANCELLED_RESERVATION, ACCEPTED}
}
