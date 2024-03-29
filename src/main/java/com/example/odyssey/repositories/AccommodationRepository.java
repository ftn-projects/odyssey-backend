package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @NonNull
    @Query("SELECT DISTINCT a " +
            "FROM Accommodation a " +
            "LEFT JOIN FETCH a.amenities am " +
            "WHERE (:guestNumber IS NULL OR :guestNumber BETWEEN a.minGuests AND a.maxGuests) " +
            "  AND (:type IS NULL OR a.type = :type) " +
            "  AND (:location IS NULL OR UPPER(a.address.city) LIKE %:location% OR UPPER(a.address.country) LIKE %:location% OR UPPER(a.address.street) LIKE %:location%) " +
            "  AND (COALESCE(:amenityIds, NULL) IS NULL OR am.id IN :amenityIds) " +
            " AND (" +
            "((cast(:reservationStartDate as localdatetime) IS NULL OR cast(:reservationEndDate as localdatetime) IS NULL))" +
            " OR" +
            "  NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM Reservation r " +
            "    WHERE r.accommodation = a " +
            "      AND (r.timeSlot.end > :reservationStartDate AND r.timeSlot.start < :reservationEndDate)) " +
            "  AND ((cast(:reservationStartDate as localdatetime) IS NULL AND cast(:reservationEndDate as localdatetime) IS NULL) OR EXISTS (" +
            "    SELECT 1 " +
            "    FROM a.availableSlots s " +
            "    WHERE (" +
            "      (s.timeSlot.end >= :reservationStartDate AND s.timeSlot.start <= :reservationEndDate)" +
            "    AND (cast(:startSlotPrice as double ) IS NULL OR cast(:endSlotPrice as double) IS NULL  OR s.price BETWEEN :startSlotPrice AND :endSlotPrice)))))"
    )
    List<Accommodation> findAllWithFilter(
            @Param("guestNumber") Integer guests,
            @Param("type") Accommodation.Type type,
            @Param("amenityIds") List<Long> amenityIds,
            @Param("reservationStartDate") LocalDateTime reservationStartDate,
            @Param("reservationEndDate") LocalDateTime reservationEndDate,
            @Param("startSlotPrice") Double startSlotPrice,
            @Param("endSlotPrice") Double endSlotPrice,
            @Param("location") String location);

    @Query("SELECT a " +
            "FROM Accommodation a " +
            "WHERE a.host.id = :hostId")
    List<Accommodation> findAllByHostId(Long hostId);

    @Query(
            "SELECT a "+
                    "FROM Accommodation  a " +
                    "WHERE a.id IN :accommodationIds"
    )

    List<Accommodation> findAllByGuestFavorites(
            @Param("accommodationIds") List<Long> accommodationIds
    );
    @Query("SELECT s.price " +
            "FROM Accommodation a " +
            "JOIN a.availableSlots s " +
            "WHERE a.id = :accommodationId " +
            "AND (cast(:startDate as localdatetime) IS NOT NULL AND cast(:endDate as localdatetime) IS NOT NULL AND (s.timeSlot.start <= :startDate AND s.timeSlot.end >= :endDate))")
    Double findPriceForDateRange(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
