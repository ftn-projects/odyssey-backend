package com.example.odyssey.services;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.repositories.ReservationRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation find(Long id) {
        return reservationRepository.findReservationsById(id);
    }

    public List<Reservation> findByAccommodation(Long id) {
        return reservationRepository.findReservationsByAccommodation_Id(id);
    }

    public List<Reservation> findByGuest(Long id) {
        return reservationRepository.findReservationsByGuest_Id(id);
    }

    public List<Reservation> findByHost(Long id) {
        return reservationRepository.findReservationsByAccommodation_Host_Id(id);
    }

    public Reservation create(Reservation reservation) {
        if (reservation.getGuestNumber() < reservation.getAccommodation().getMinGuests() ||
                reservation.getGuestNumber() > reservation.getAccommodation().getMaxGuests())
            throw new ValidationException("Reservation guest number is invalid.");

        if (overlapsReservation(reservation.getAccommodation().getId(), reservation.getTimeSlot()))
            throw new ValidationException("Accommodation is not available for selected period.");
        return reservationRepository.save(reservation);
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> filter(List<Reservation> reservations, Long accommodationId, Reservation.Status status, LocalDateTime start, LocalDateTime end) {
        if (accommodationId != null)
            reservations.retainAll(reservationRepository.findReservationsByAccommodation_Id(accommodationId));
        if (status != null) reservations.retainAll(reservationRepository.findReservationsByStatus(status));
        if (start != null) reservations.retainAll(reservationRepository.findReservationsByTimeSlot_Start(start));
        if (end != null) reservations.retainAll(reservationRepository.findReservationsByTimeSlot_End(end));

        return reservations;
    }

    public List<Reservation> getFiltered(
            Long hostId,
            List<String> status,
            String title,
            Long dateStart,
            Long dateEnd
    ){
        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        List<Reservation.Status> statuses = new ArrayList<>();
        if(status != null && !status.isEmpty()){
            for(String s:status) statuses.add(Reservation.Status.valueOf(s));
            return reservationRepository.findAllWithFilter(hostId, statuses,title,startDate,endDate);
        }
        return reservationRepository.findAllWithFilter(hostId, null,title,startDate,endDate);
    }

    public LocalDateTime convertToDate(Long date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), TimeZone.getDefault().toZoneId());
    }

    public boolean overlapsReservation(Long accommodationId, TimeSlot slot) {
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for (Reservation i : reservations)
            if (i.getTimeSlot().overlaps(slot) && i.getStatus().equals(Reservation.Status.ACCEPTED))
                return true;
        return false;
    }

    public void cancelOverlapping(Long accommodationId, Reservation reservation){
        if(!reservation.getStatus().equals(Reservation.Status.ACCEPTED)) return;
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for(Reservation i:reservations){
            if (i.getTimeSlot().overlaps(reservation.getTimeSlot()) && i.getStatus().equals(Reservation.Status.REQUESTED)){
                i.setStatus(Reservation.Status.DECLINED);
                save(i);
            }
        }
    }
}
