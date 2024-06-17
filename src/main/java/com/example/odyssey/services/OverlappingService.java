package com.example.odyssey.services;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverlappingService {

    @Autowired
    private ReservationRepository reservationRepository;

    public boolean overlapsReservation(Long accommodationId, TimeSlot timeSlot) {
        return reservationRepository.findReservationsByAccommodation_Id(accommodationId).stream()
                .anyMatch(reservation -> reservation.getTimeSlot().overlaps(timeSlot));
    }
}
