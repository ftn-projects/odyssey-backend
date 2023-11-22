package com.example.odyssey.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    private LocalDateTime start;
    private LocalDateTime end;

    public Duration getDuration() {
        return Duration.between(start, end);
    }
}
