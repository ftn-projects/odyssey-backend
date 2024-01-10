package com.example.odyssey.dtos.reservation;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reservations.Reservation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationsAccreditDTO {
    private Long id;
    @Positive(message = "Price must be positive.")
    private Double price;
    @Positive(message = "Guest number must be positive.")
    private Integer guestNumber;
    private Integer cancellationNumber;
    private Reservation.Status status;
    private LocalDate requestDate;
    private LocalDate start;
    private LocalDate end;
    @NotNull(message = "Accommodation must be provided.")
    private AccommodationDTO accommodation;
    @NotNull(message = "Guest must be provided.")
    private UserDTO guest;

    public ReservationsAccreditDTO(Reservation reservation, int cancel) {
        id = reservation.getId();
        price = reservation.getPrice();
        guestNumber = reservation.getGuestNumber();
        cancellationNumber = cancel;
        status = reservation.getStatus();
        requestDate = reservation.getRequestDate().toLocalDate();
        start = reservation.getTimeSlot().getStart().toLocalDate();
        end = reservation.getTimeSlot().getEnd().toLocalDate();
        accommodation = new AccommodationDTO(reservation.getAccommodation());
        guest = new UserDTO(reservation.getGuest());
    }
}
