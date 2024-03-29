package com.example.odyssey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {
    @Column(name = "start_date")
    private LocalDateTime start;
    @Column(name = "end_date")
    private LocalDateTime end;

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    public boolean overlaps(TimeSlot slot){
        return (this.getStart().isBefore(slot.getEnd()) && this.getEnd().isAfter(slot.getStart()));
    }
    public boolean containsDay(LocalDate day){
        return !(this.getStart().toLocalDate().isAfter(day) && this.getEnd().toLocalDate().isBefore(day));
    }
    public List<LocalDate> getDays(){
        return start.toLocalDate().datesUntil(end.toLocalDate()).collect(Collectors.toList());
    }
}
