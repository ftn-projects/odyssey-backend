package com.example.odyssey.repositories;

import com.example.odyssey.entity.reservations.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @NonNull Page<Reservation> findAll(@NonNull Pageable pageable);

    Reservation findReservationsById(Long id);
    List<Reservation> findReservationsByAccommodation_Id(Long id);
    List<Reservation> findReservationsByGuest_Id(Long id);
    List<Reservation> findReservationsByAccommodation_Host_Id(Long id);
    List<Reservation> findReservationsByStatus(Reservation.Status status);
    List<Reservation> findReservationsByTimeSlot_Start(LocalDateTime start);
    List<Reservation> findReservationsByTimeSlot_End(LocalDateTime end);
}
