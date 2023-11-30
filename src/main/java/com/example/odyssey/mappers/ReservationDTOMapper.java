package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reservations.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReservationDTOMapper(ModelMapper mapper) {
        ReservationDTOMapper.mapper = mapper;
    }

    public static Reservation fromDTOtoReservation(ReservationDTO dto) {
        return mapper.map(dto, Reservation.class);
    }

    public static ReservationDTO fromReservationToDTO(Reservation reservation) {
        return new ReservationDTO(reservation);
    }
}
