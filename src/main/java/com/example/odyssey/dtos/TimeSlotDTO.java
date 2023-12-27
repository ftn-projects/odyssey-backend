package com.example.odyssey.dtos;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.validation.TimeSlotConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TimeSlotConstraint
public class TimeSlotDTO {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeSlotDTO(TimeSlot address) {
        start = address.getStart();
        end = address.getEnd();
    }
}
