package com.example.odyssey.dtos.reservation;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequestCreationDTO {
    Long accommodationId;
    TimeSlot timeSlot;
    Integer guestNumber;
    UserDTO guest;

    public ReservationRequestCreationDTO(Reservation reservation){
        accommodationId = reservation.getAccommodation().getId();
        timeSlot = reservation.getTimeSlot();
        guestNumber = reservation.getGuestNumber();
        guest = new UserDTO(reservation.getGuest());
    }
}
