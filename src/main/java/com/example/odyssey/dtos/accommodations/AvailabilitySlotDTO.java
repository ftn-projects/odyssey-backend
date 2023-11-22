package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AvailabilitySlotDTO {
    private Double price;
    private TimeSlotDTO timeSlot;

    public AvailabilitySlotDTO(AvailabilitySlot slot) {
        price = slot.getPrice();
        timeSlot = new TimeSlotDTO(slot.getTimeSlot());
    }
}
