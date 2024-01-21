package com.example.odyssey.dtos.statistics;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.users.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationTotalStatsDTO {
    Long start;
    Long end;
    Integer totalReservations;
    Double totalIncome;
    AccommodationDTO accommodation;
    List<MonthlyStatsDTO> monthlyStats;
}
