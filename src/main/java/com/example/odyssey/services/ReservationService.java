package com.example.odyssey.services;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.exceptions.reservations.FailedCancellationException;
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

    public List<Reservation> getFilteredByHost(
            Long hostId,
            List<String> status,
            String title,
            Long dateStart,
            Long dateEnd
    ) {
        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        List<Reservation.Status> statuses = new ArrayList<>();
        if (title != null) title = title.toUpperCase();

        if (status != null && !status.isEmpty()) {
            for (String s : status) statuses.add(Reservation.Status.valueOf(s));
            return reservationRepository.findAllWithFilter(hostId, statuses, title, startDate, endDate);
        }
        return reservationRepository.findAllWithFilter(hostId, null, title, startDate, endDate);
    }

    public List<Reservation> getFilteredByGuest(
            Long guestId,
            List<String> status,
            String title,
            Long dateStart,
            Long dateEnd
    ) {
        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        List<Reservation.Status> statuses = new ArrayList<>();
        if (title != null) title = title.toUpperCase();

        if (status != null && !status.isEmpty()) {
            for (String s : status) statuses.add(Reservation.Status.valueOf(s));
            return reservationRepository.findAllWithGuestFilter(guestId, statuses, title, startDate, endDate);
        }
        return reservationRepository.findAllWithGuestFilter(guestId, null, title, startDate, endDate);
    }


    public LocalDateTime convertToDate(Long date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date), TimeZone.getDefault().toZoneId());
    }

    public void updateStatus(Reservation reservation, String status) {
        if (status.equals("CANCELLED_REQUEST") || status.equals("CANCELLED_RESERVATION")) {
            if (reservation.getTimeSlot().getStart().isAfter(
                    reservation.getTimeSlot().getStart().minusDays(
                            reservation.getAccommodation().getCancellationDue().toDays())))
                throw new FailedCancellationException("because the due date has already passed.");
        }
        reservation.setStatus(Reservation.Status.valueOf(status));
        reservation = save(reservation);
        cancelOverlapping(reservation.getAccommodation().getId(), reservation);
    }

    public boolean overlapsReservation(Long accommodationId, TimeSlot slot) {
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for (Reservation i : reservations)
            if (i.getTimeSlot().overlaps(slot) && i.getStatus().equals(Reservation.Status.ACCEPTED))
                return true;
        return false;
    }

    public int getCancellationNumber(Long guestId) {
        int cancels = 0;
        for (Reservation r : findByGuest(guestId)) {
            if (r.getStatus().equals(Reservation.Status.CANCELLED_RESERVATION)) cancels += 1;
        }
        return cancels;
    }

    public void cancelOverlapping(Long accommodationId, Reservation reservation) {
        if (!reservation.getStatus().equals(Reservation.Status.ACCEPTED)) return;
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for (Reservation i : reservations) {
            if (i.getTimeSlot().overlaps(reservation.getTimeSlot()) && i.getStatus().equals(Reservation.Status.REQUESTED)) {
                i.setStatus(Reservation.Status.DECLINED);
                save(i);
            }
        }
    }

    public void declineExpiredReservations() {
        for (Reservation r : getAll()) {
            if (r.getStatus().equals(Reservation.Status.REQUESTED) && !r.getTimeSlot().getStart().isAfter(LocalDateTime.now())) {
                r.setStatus(Reservation.Status.DECLINED);
                save(r);
            }
        }
    }

    public void automaticApproval(Reservation r) {
        if (r.getAccommodation().getAutomaticApproval()) {
            r.setStatus(Reservation.Status.ACCEPTED);
            save(r);
            cancelOverlapping(r.getAccommodation().getId(), r);
        }
    }

    public void declineAllForGuest(Long guestId) {
        findByGuest(guestId).stream().filter(r ->
                r.getTimeSlot().getEnd().isAfter(LocalDateTime.now()) &&
                        (r.getStatus().equals(Reservation.Status.REQUESTED) ||
                                r.getStatus().equals(Reservation.Status.ACCEPTED))
        ).forEach(r -> {
            r.setStatus(Reservation.Status.DECLINED);
            save(r);
        });
    }
}
