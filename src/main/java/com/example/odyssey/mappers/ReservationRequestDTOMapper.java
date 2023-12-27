package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reservation.ReservationRequestDTO;
import com.example.odyssey.entity.reservations.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservationRequestDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReservationRequestDTOMapper(ModelMapper mapper) {
        ReservationRequestDTOMapper.mapper = mapper;
    }

    public static Reservation fromDTOtoReservation(ReservationRequestDTO dto) {
        return mapper.map(dto, Reservation.class);
    }

    public static ReservationRequestDTO fromReservationToDTO(Reservation reservation) {
        return new ReservationRequestDTO(reservation);
    }
}
