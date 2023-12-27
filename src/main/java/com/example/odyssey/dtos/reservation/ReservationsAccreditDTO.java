package com.example.odyssey.dtos.reservation;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reservations.Reservation;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsAccreditDTO {
    private Long id;
    private Double price;
    private Integer guestNumber;
    private Reservation.Status status;
    private LocalDate requestDate;
    private LocalDate start;
    private LocalDate end;
    private AccommodationDTO accommodation;
    private UserDTO guest;

    public ReservationsAccreditDTO(Reservation reservation) {
        id = reservation.getId();
        price = reservation.getPrice();
        guestNumber = reservation.getGuestNumber();
        status = reservation.getStatus();
        requestDate = reservation.getRequestDate().toLocalDate();
        start = reservation.getTimeSlot().getStart().toLocalDate();
        end = reservation.getTimeSlot().getEnd().toLocalDate();
        accommodation = new AccommodationDTO(reservation.getAccommodation());
        guest = new UserDTO(reservation.getGuest());
    }
}
