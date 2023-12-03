package com.example.odyssey.services;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll () {return reservationRepository.findAll();}
    public Reservation findByAccommodation(Long id){return reservationRepository.findReservationByAccommodation_Id(id);}
    public Reservation findByGuest(Long id){return reservationRepository.findReservationByGuest_Id(id);}
    public Reservation findByHost(Long id){return reservationRepository.findReservationByAccommodation_Host_Id(id);}
    public Reservation save(Reservation reservation){return reservationRepository.save(reservation);}
}
