package com.example.odyssey.dtos.reservations;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
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
public class ReservationDTO {
    private Long id;
    @NotNull(message = "Reservation price must be set.")
    @Positive(message = "Reservation price must be a positive integer.")
    private Double price;
    @NotNull(message = "Reservation guest number must be set.")
    @Positive(message = "Guest number must be a positive integer.")
    private Integer guestNumber;
    @NotNull(message = "Reservation status must be set.")
    private Reservation.Status status;
    @NotNull(message = "Reservation request date must be set.")
    private LocalDateTime requestDate;
    @NotNull(message = "Reservation date must be set.")
    private LocalDateTime reservationDate;
    @NotNull(message = "Reservation timeslot must be set.")
    private TimeSlotDTO timeSlot;
    @NotNull(message = "Reservation accommodation must be set.")
    private AccommodationDTO accommodation;
    @NotNull(message = "Reservation guest must be set.")
    private UserDTO guest;

    public ReservationDTO(Reservation reservation) {
        id = reservation.getId();
        price = reservation.getPrice();
        guestNumber = reservation.getGuestNumber();
        status = reservation.getStatus();
        requestDate = reservation.getRequestDate();
        reservationDate = reservation.getReservationDate();
        timeSlot = new TimeSlotDTO(reservation.getTimeSlot());
        accommodation = new AccommodationDTO(reservation.getAccommodation());
        guest = new UserDTO(reservation.getGuest());
    }
}
