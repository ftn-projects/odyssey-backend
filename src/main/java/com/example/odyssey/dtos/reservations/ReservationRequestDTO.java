package com.example.odyssey.dtos.reservations;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.entity.reservations.Reservation;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Double price;
    @NotNull
    private Integer guestNumber;
    @NotNull
    private Reservation.Status status;
    @NotNull
    private LocalDateTime requestDate;
    @NotNull
    private TimeSlotDTO timeSlot;
    @NotNull
    private Long accommodationId;
    @NotNull
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
