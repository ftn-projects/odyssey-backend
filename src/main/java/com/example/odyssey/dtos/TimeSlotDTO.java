package com.example.odyssey.dtos;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlotDTO {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeSlotDTO(TimeSlot address) {
        start = address.getStart();
        end = address.getEnd();
    }
}
