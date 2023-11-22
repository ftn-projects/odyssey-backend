package com.example.odyssey.dtos.reservation;

import com.example.odyssey.dtos.users.ResponseUserDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseReservationDTO {
    Long accommodationId;
    TimeSlot timeSlot;
    Integer guestNumber;
    ResponseUserDTO guest;

    public ResponseReservationDTO(Reservation reservation){
        accommodationId = reservation.getAccommodation().getId();
        timeSlot = reservation.getTimeSlot();
        guestNumber = reservation.getGuestNumber();
        guest = new ResponseUserDTO(reservation.getGuest());
    }
}
