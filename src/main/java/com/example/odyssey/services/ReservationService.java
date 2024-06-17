package com.example.odyssey.services;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.exceptions.ValidationException;
import com.example.odyssey.exceptions.reservations.FailedCancellationException;
import com.example.odyssey.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TimeZone;

import static com.example.odyssey.entity.reservations.Reservation.Status.ACCEPTED;
import static com.example.odyssey.entity.reservations.Reservation.Status.REQUESTED;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation find(Long id) {
        return reservationRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Reservation with id " + id + " not found."));
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

        if (reservation.getTimeSlot().getStart().isBefore(LocalDateTime.now()))
            throw new ValidationException("Reservation start date is in the past.");

        if (reservation.getTimeSlot().getStart().isAfter(reservation.getTimeSlot().getEnd()))
            throw new ValidationException("Reservation start date is after end date.");

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

    public Reservation updateStatus(Long id, String status) {
        var reservation = find(id);

        if (status.equals("CANCELLED_REQUEST") || status.equals("CANCELLED_RESERVATION")) {
            if (LocalDateTime.now().isAfter(reservation.getTimeSlot().getStart().minusDays(
                    reservation.getAccommodation().getCancellationDue().toDays())))
                throw new FailedCancellationException("because due date has already passed.");
        }

        if (status.equals("DECLINED") && reservation.getStatus().equals(Reservation.Status.DECLINED))
            throw new ValidationException("Reservation has already been declined.");
        else if (status.equals("CANCELLED_REQUEST") && reservation.getStatus().equals(Reservation.Status.CANCELLED_REQUEST))
            throw new ValidationException("Reservation request has already been cancelled.");
        else if (status.equals("CANCELLED_RESERVATION") && reservation.getStatus().equals(Reservation.Status.CANCELLED_RESERVATION))
            throw new ValidationException("Reservation has already been cancelled.");

        reservation.setStatus(Reservation.Status.valueOf(status));
        return save(reservation);
    }

    public Reservation accept(Long id) {
        Reservation reservation = find(id);

        if (reservation.getStatus().equals(ACCEPTED))
            throw new ValidationException("Reservation has already been accepted.");
        if (!reservation.getStatus().equals(REQUESTED))
            throw new ValidationException("Reservation status is not requested.");

        reservation.setStatus(ACCEPTED);
        reservation = save(reservation);

        var overlapping = getOverlapping(reservation.getAccommodation().getId(), reservation);
        for (Reservation r : overlapping) {
            r.setStatus(Reservation.Status.DECLINED);
            save(r);
        }

        return reservation;
    }

    public boolean overlapsReservation(Long accommodationId, TimeSlot slot) {
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for (Reservation i : reservations)
            if (i.getTimeSlot().overlaps(slot) && i.getStatus().equals(ACCEPTED))
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

    public List<Reservation> getOverlapping(Long accommodationId, Reservation reservation) {
        var overlapping = new ArrayList<Reservation>();
        List<Reservation> reservations = findByAccommodation(accommodationId);
        for (Reservation i : reservations) {
            if (i.getTimeSlot().overlaps(reservation.getTimeSlot()) && i.getStatus().equals(Reservation.Status.REQUESTED)) {
                overlapping.add(i);
            }
        }
        return overlapping;
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
            r.setStatus(ACCEPTED);
            save(r);
            var overlapping = getOverlapping(r.getAccommodation().getId(), r);
            for (Reservation i : overlapping) {
                i.setStatus(Reservation.Status.DECLINED);
                save(i);
            }
        }
    }

    public void declineAllForGuest(Long guestId) {
        findByGuest(guestId).stream().filter(r ->
                r.getTimeSlot().getEnd().isAfter(LocalDateTime.now()) &&
                        (r.getStatus().equals(Reservation.Status.REQUESTED) ||
                                r.getStatus().equals(ACCEPTED))
        ).forEach(r -> {
            r.setStatus(Reservation.Status.DECLINED);
            save(r);
        });
    }

}
