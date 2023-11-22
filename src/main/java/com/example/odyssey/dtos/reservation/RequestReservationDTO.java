package com.example.odyssey.dtos.reservation;

import com.example.odyssey.entity.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestReservationDTO {
    private Long accommodationId;
    private TimeSlot timeSlot;
    private Integer guestNumber;
    private Long guestId;
}
