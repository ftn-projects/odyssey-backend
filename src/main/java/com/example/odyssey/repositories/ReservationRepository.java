package com.example.odyssey.repositories;

import com.example.odyssey.entity.reservations.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
//filter po statusun terminu i smestaju

    @NonNull Page<Reservation> findAll(@NonNull Pageable pageable);
    Reservation findReservationByAccommodation_Id(Long id);
    Reservation findReservationByGuest_Id(Long id);
    Reservation findReservationByAccommodation_Host_Id(Long id);

}
