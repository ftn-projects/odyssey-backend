package com.example.odyssey.repository;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.repositories.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldSaveReservation() {
        Reservation reservation = new Reservation(null, 150.0, 2, Reservation.Status.ACCEPTED,
                LocalDateTime.now(), LocalDateTime.now().plusDays(3),
                new TimeSlot(LocalDateTime.now(), LocalDateTime.now().plusHours(2)), new Accommodation(), new Guest());
        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation).usingRecursiveComparison().ignoringFields("id").isEqualTo(reservation);
    }
}
