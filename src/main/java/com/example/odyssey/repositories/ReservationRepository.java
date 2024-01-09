package com.example.odyssey.repositories;

import com.example.odyssey.entity.reservations.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT DISTINCT r " +
            "FROM Reservation  r " +
            "LEFT JOIN FETCH r.guest g " +
            "LEFT JOIN FETCH  r.accommodation a " +
            "WHERE (r.accommodation.host.id = :hostId) " +
            "AND (:status IS NULL OR r.status IN :status) " +
            "AND (:title IS NULL OR UPPER(r.accommodation.title) LIKE %:title%) " +
            "AND ((cast(:reservationStartDate as localdatetime) IS NULL OR cast(:reservationEndDate as localdatetime) IS NULL) " +
            "OR (r.timeSlot.end <= :reservationEndDate AND r.timeSlot.start >= :reservationStartDate))")
    List<Reservation> findAllWithFilter(
            @Param("hostId") Long hostId,
            @Param("status") List<Reservation.Status> status,
            @Param("title") String title,
            @Param("reservationStartDate") LocalDateTime reservationStartDate,
            @Param("reservationEndDate") LocalDateTime reservationEndDate);

    @Query("SELECT DISTINCT r " +
            "FROM Reservation  r " +
            "LEFT JOIN FETCH r.guest g " +
            "LEFT JOIN FETCH  r.accommodation a " +
            "WHERE (r.guest.id = :guestId) " +
            "AND (:status IS NULL OR r.status IN :status) " +
            "AND (:title IS NULL OR UPPER(r.accommodation.title) LIKE %:title%) " +
            "AND ((cast(:reservationStartDate as localdatetime) IS NULL OR cast(:reservationEndDate as localdatetime) IS NULL) " +
            "OR (r.timeSlot.end <= :reservationEndDate AND r.timeSlot.start >= :reservationStartDate))")
    List<Reservation> findAllWithGuestFilter(
            @Param("guestId") Long guestId,
            @Param("status") List<Reservation.Status> status,
            @Param("title") String title,
            @Param("reservationStartDate") LocalDateTime reservationStartDate,
            @Param("reservationEndDate") LocalDateTime reservationEndDate
    );

}
