package com.example.odyssey.dtos.reservation;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reservations.Reservation;
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
    private Double price;
    private Integer guestNumber;
    private Reservation.Status status;
    private LocalDateTime requestDate;
    private LocalDateTime reservationDate;
    private TimeSlotDTO timeSlot;
    private AccommodationDTO accommodation;
    private UserDTO guest;

    public ReservationDTO(Reservation reservation) {
        id = reservation.getId();
        guestNumber = reservation.getGuestNumber();
        status = reservation.getStatus();
        timeSlot = new TimeSlotDTO(reservation.getTimeSlot());
        accommodation = new AccommodationDTO(reservation.getAccommodation());
        guest = new UserDTO(reservation.getGuest());
    }
}
