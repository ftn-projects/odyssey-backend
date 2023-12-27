package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reservation.ReservationsAccreditDTO;
import com.example.odyssey.entity.reservations.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccreditReservationDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public AccreditReservationDTOMapper(ModelMapper mapper) {AccreditReservationDTOMapper.mapper = mapper;}

    public static Reservation fromDTOtoReservation(ReservationsAccreditDTO dto){
        return mapper.map(dto, Reservation.class);
    }

    public static ReservationsAccreditDTO fromReservationToDTO(Reservation reservation){
        return new ReservationsAccreditDTO(reservation);
    }
}
