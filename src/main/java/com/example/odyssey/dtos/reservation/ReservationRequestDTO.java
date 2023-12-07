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
public class ReservationRequestDTO {
    private Long id;
    private Double price;
    private Integer guestNumber;
    private Reservation.Status status;
    private LocalDateTime requestDate;
    private TimeSlotDTO timeSlot;
    private Long accommodationId;
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
