package com.example.odyssey.dtos.statistics;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.users.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalStatsDTO {
    Long start;
    Long end;
    UserDTO host;
    Integer totalAccommodations;
    Integer totalReservations;
    Double totalIncome;
    List<MonthlyStatsDTO> monthlyStats;
}
