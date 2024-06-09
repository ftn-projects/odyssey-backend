package com.example.odyssey.dtos.reservations;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.entity.reservations.Reservation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestDTO {
    private Long id;
    @NotNull(message = "Reservation request price must be set.")
    @Positive(message = "Reservation request price must be a positive integer.")
    private Double price;
    @NotNull(message = "Reservation guest number must be set.")
    @Positive(message = "Guest number must be a positive integer.")
    private Integer guestNumber;
    @NotNull(message = "Reservation request status must be set.")
    private Reservation.Status status;
    @NotNull(message = "Reservation request date must be set.")
    private LocalDateTime requestDate;
    @NotNull(message = "Reservation request timeslot must be set.")
    private TimeSlotDTO timeSlot;
    @NotNull(message = "Reservation request accommodation must be set.")
    private Long accommodationId;
    @NotNull(message = "Reservation request guest must be set.")
    private Long guestId;

    public ReservationRequestDTO(Reservation reservation) {
        id = reservation.getId();
        price = reservation.getPrice();
        guestNumber = reservation.getGuestNumber();
        status = reservation.getStatus();
        requestDate = reservation.getRequestDate();
        timeSlot = new TimeSlotDTO(reservation.getTimeSlot());
        accommodationId = reservation.getAccommodation().getId();
        guestId = reservation.getGuest().getId();
    }
}
