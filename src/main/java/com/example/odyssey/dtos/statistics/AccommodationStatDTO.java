package com.example.odyssey.dtos.statistics;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationStatDTO {
    AccommodationDTO accommodation;
    List<MonthDetails> details = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthDetails {
        Double income;
        Integer reservationCount;
    }
}
