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
import java.util.List;
import java.util.Set;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @NonNull
    @Query("SELECT DISTINCT a " +
            "FROM Accommodation a " +
            "JOIN FETCH a.amenities am " +
            "WHERE (:startGuests IS NULL OR a.minGuests BETWEEN :startGuests AND :endGuests) " +
            "  AND (:endGuests IS NULL OR a.maxGuests BETWEEN :startGuests AND :endGuests) " +
            "  AND (:type IS NULL OR a.type = :type) " +
            "  AND (COALESCE(:amenityIds, NULL) IS NULL OR am.id IN :amenityIds) " +
            "  AND NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM Reservation r " +
            "    WHERE r.accommodation = a " +
            "      AND (:reservationStartDate IS NULL OR r.endDate > :reservationStartDate) " +
            "      AND (:reservationEndDate IS NULL OR r.startDate < :reservationEndDate) " +
            "  ) " +
            "  AND EXISTS (" +
            "    SELECT 1 " +
            "    FROM a.availableSlots s " +
            "    WHERE (COALESCE(:reservationStartDate, :reservationEndDate, NULL) IS NULL " +
            "            OR (s.timeSlot.start <= :reservationEndDate AND s.timeSlot.end >= :reservationStartDate)) " +
            "      AND (:startSlotPrice IS NULL OR s.price BETWEEN :startSlotPrice AND :endSlotPrice)")
    List<Accommodation> findAccommodationsWithFilter(
            @Param("startGuests") Integer startGuests,
            @Param("endGuests") Integer endGuests,
            @Param("type") Accommodation.Type type,
            @Param("amenityIds") List<Long> amenityIds,
            @Param("reservationStartDate") LocalDate reservationStartDate,
            @Param("reservationEndDate") LocalDate reservationEndDate,
            @Param("startSlotPrice") Double startSlotPrice,
            @Param("endSlotPrice") Double endSlotPrice);


    Accommodation findOneById(Long id);

    List<Accommodation> findAllByHost(Host host);

    List<Accommodation> findByTitleContaining(String title);

    List<Accommodation> findAllByAmenitiesContaining(Set<Amenity> amenities);

    List<Accommodation> findByMinGuestsLessThanEqualAndMaxGuestsGreaterThanEqual(int minGuests, int maxGuests);

    List<Accommodation> findByDefaultPriceBetween(Double priceStart, Double priceEnd);

    List<Accommodation> findAllByType(Accommodation.Type type);
}
