package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @NonNull
    @Query("SELECT DISTINCT a " +
            "FROM Accommodation a " +
            "JOIN FETCH a.amenities am " +
            "WHERE (:guestNumber IS NULL OR :guestNumber BETWEEN a.minGuests AND a.maxGuests) " +
            "  AND (:type IS NULL OR a.type = :type) " +
            "  AND (COALESCE(:amenityIds, NULL) IS NULL OR am IN :amenityIds) " +
            "  AND NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM Reservation r " +
            "    WHERE r.accommodation = a " +
            "      AND (cast(:reservationStartDate as localdatetime) IS NOT NULL AND cast(:reservationEndDate as localdatetime) IS NOT NULL) " +
            "      AND (r.timeSlot.end > :reservationStartDate AND r.timeSlot.start < :reservationEndDate)) " +
            "  AND EXISTS (" +
            "    SELECT 1 " +
            "    FROM a.availableSlots s " +
            "    WHERE (" +
            "      (cast(:reservationStartDate as localdatetime) IS NOT NULL AND cast(:reservationEndDate as localdatetime) IS NOT NULL) " +
            "      AND (s.timeSlot.end >= :reservationStartDate OR s.timeSlot.start <= :reservationEndDate)" +
            "    )" +
            "    AND ((cast(:startSlotPrice as double ) IS NULL OR cast(:endSlotPrice as double) IS NULL ) OR s.price BETWEEN :startSlotPrice AND :endSlotPrice))")
    List<Accommodation> findAllWithFilter(
            @Param("guestNumber") Integer guests,
            @Param("type") Accommodation.Type type,
            @Param("amenityIds") List<Amenity> amenityIds,
            @Param("reservationStartDate") LocalDateTime reservationStartDate,
            @Param("reservationEndDate") LocalDateTime reservationEndDate,
            @Param("startSlotPrice") Double startSlotPrice,
            @Param("endSlotPrice") Double endSlotPrice);

    Accommodation findOneById(Long id);
    List<Accommodation> findAllByHost(Host host);
}
