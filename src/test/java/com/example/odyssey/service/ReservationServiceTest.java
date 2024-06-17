package com.example.odyssey.service;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.exceptions.ValidationException;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.services.AccommodationRequestService;
import com.example.odyssey.services.ReservationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.odyssey.entity.reservations.Reservation.Status.ACCEPTED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationService service;

    @Test
    void shouldThrowNoSuchElementExceptionForInvalidId() {
        when(reservationRepository.findById(99L)).thenReturn(Optional.empty());

        var thrown = assertThrows(NoSuchElementException.class, () -> service.accept(99L));

        assertEquals("Reservation with id 99 not found.", thrown.getMessage());
    }

    @Test
    void shouldThrowValidationExceptionForAlreadyAcceptedReservations() {
        var reservation = new Reservation();
        reservation.setStatus(ACCEPTED);
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        var thrown = assertThrows(ValidationException.class, () -> service.accept(1L));

        assertEquals("Reservation has already been accepted.", thrown.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"DECLINED", "CANCELLED_REQUEST", "CANCELLED_RESERVATION"})
    void shouldThrowValidationExceptionForReservationThatIsNotRequested() {
        var reservation = new Reservation();
        reservation.setStatus(Reservation.Status.valueOf("DECLINED"));
        when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

        var thrown = assertThrows(ValidationException.class, () -> service.accept(1L));

        assertEquals("Reservation status is not requested.", thrown.getMessage());
    }

    @Test
    void shouldAcceptRequestedReservationAndDeclineOverlappingReservations() {
        Long accommodationId = 1L;
        var reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        var accommodation = new Accommodation();
        accommodation.setId(accommodationId);
        reservation.setAccommodation(accommodation);
        reservation.setTimeSlot(new TimeSlot(
                LocalDateTime.of(2023, 6, 17, 10, 0),
                LocalDateTime.of(2023, 6, 17, 12, 0)
        ));

        var overlappingReservation = new Reservation();
        overlappingReservation.setStatus(Reservation.Status.REQUESTED);
        overlappingReservation.setAccommodation(accommodation);
        overlappingReservation.setTimeSlot(new TimeSlot(
                LocalDateTime.of(2023, 6, 17, 11, 0),
                LocalDateTime.of(2023, 6, 17, 13, 0)
        ));

        when(reservationRepository.findById(eq(1L))).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(service.getOverlapping(accommodationId, any())).thenReturn(List.of(overlappingReservation));

        Reservation acceptedReservation = service.accept(1L);

        assertEquals(Reservation.Status.ACCEPTED, acceptedReservation.getStatus());
        verify(reservationRepository, times(2)).save(any(Reservation.class));
        assertEquals(Reservation.Status.DECLINED, overlappingReservation.getStatus());
    }

    @Test
    void shouldHandleNoOverlappingReservations() {
        Long accommodationId = 1L;
        var reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        var accommodation = new Accommodation();
        accommodation.setId(accommodationId);
        reservation.setAccommodation(accommodation);
        reservation.setTimeSlot(new TimeSlot(
                LocalDateTime.of(2023, 6, 17, 10, 0),
                LocalDateTime.of(2023, 6, 17, 12, 0)
        ));

        when(reservationRepository.findById(eq(1L))).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(service.getOverlapping(accommodationId, any())).thenReturn(Collections.emptyList());

        Reservation acceptedReservation = service.accept(1L);

        assertEquals(Reservation.Status.ACCEPTED, acceptedReservation.getStatus());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
}

