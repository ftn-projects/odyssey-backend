package com.example.odyssey.entity.accommodations;

import com.example.odyssey.entity.TimeSlot;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilitySlot {
    private Double price;
    @Embedded
    private TimeSlot timeSlot;
}
