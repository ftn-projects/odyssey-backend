package com.example.odyssey.service;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.repositories.AccommodationRepository;
import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.services.OverlappingService;
import com.example.odyssey.services.ReservationService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private OverlappingService overlappingService;

    @Mock
    private AccommodationRepository accommodationRepository;

    Accommodation mockAccommodation = new Accommodation();

    @BeforeEach
    public void setUp() {
        mockAccommodation.setId(1L);
        mockAccommodation.setMinGuests(2);
        mockAccommodation.setMaxGuests(4);
        mockAccommodation.setHost(null);
        mockAccommodation.setAutomaticApproval(true);
        mockAccommodation.setAvailableSlots(null);
        List<AvailabilitySlot> slots =
                        List.of(new AvailabilitySlot(520.0, new TimeSlot(
                                LocalDateTime.of(2024, Month.JULY, 1, 0, 0, 0),
                                LocalDateTime.of(2024, Month.JULY, 20, 0, 0, 0)
                        )),
                        new AvailabilitySlot(450.0, new TimeSlot(
                                LocalDateTime.of(2024, Month.JULY, 21, 0, 0, 0),
                                LocalDateTime.of(2024, Month.AUGUST, 9, 0, 0, 0)
                        )),
                        new AvailabilitySlot(430.0, new TimeSlot(
                                LocalDateTime.of(2024, Month.AUGUST, 10, 0, 0, 0),
                                LocalDateTime.of(2024, Month.AUGUST, 29, 0, 0, 0)
                        ))
                );
        mockAccommodation.setAvailableSlots(Set.of(slots.get(0), slots.get(1), slots.get(2)));
        mockAccommodation.setAmenities(null);
        mockAccommodation.setImages(null);
        mockAccommodation.setPricing(null);
        mockAccommodation.setDefaultPrice(0.0);
        mockAccommodation.setCancellationDue(null);
    }

    TimeSlot VALID_SLOT = new TimeSlot(
            LocalDateTime.of(2024, Month.JULY,2,0,0,0),
            LocalDateTime.of(2024,Month.JULY,7,0,0,0));


    TimeSlot INVALID_SLOT = new TimeSlot(
            LocalDateTime.of(2024, Month.JULY, 18, 0, 0, 0),
            LocalDateTime.of(2024, Month.JULY, 25, 0, 0, 0)
            );

    TimeSlot PAST_INVALID_SLOT = new TimeSlot(
            LocalDateTime.of(2024, Month.JUNE, 14, 0, 0, 0),
            LocalDateTime.of(2024, Month.JUNE, 20, 0, 0, 0)
    );

    TimeSlot START_AFTER_END_SLOT = new TimeSlot(
            LocalDateTime.of(2024, Month.JUNE, 24, 0, 0, 0),
            LocalDateTime.of(2024, Month.JUNE, 20, 0, 0, 0)
    );


    @DisplayName("Test successful reservation creation.")
    @Test
    public void ValidReservationCreation(){
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setPrice(300D);
        reservation.setTimeSlot(VALID_SLOT);
        reservation.setGuest(null);
        reservation.setGuestNumber(3);
        reservation.setAccommodation(mockAccommodation);

        reservationService.create(reservation);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @DisplayName("Test reservation creation when guest number is invalid.")
    @Test
    public void InvalidGuestNumberCreation(){
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setPrice(300D);
        reservation.setTimeSlot(VALID_SLOT);
        reservation.setGuest(null);
        reservation.setGuestNumber(6);
        reservation.setAccommodation(mockAccommodation);

        Throwable exception = assertThrows(ValidationException.class, () -> reservationService.create(reservation));
        assertEquals("Reservation guest number is invalid.", exception.getMessage());
        verify(reservationRepository, times(0)).save(reservation);
    }

    @DisplayName("Test reservation creation when start date is in the past.")
    @Test
    public void StartDayPastCreation(){
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setPrice(300D);
        reservation.setTimeSlot(PAST_INVALID_SLOT);
        reservation.setGuest(null);
        reservation.setGuestNumber(4);
        reservation.setAccommodation(mockAccommodation);

        Throwable exception = assertThrows(ValidationException.class, () -> reservationService.create(reservation));
        assertEquals("Reservation start date is in the past.", exception.getMessage());
        verify(reservationRepository, times(0)).save(reservation);
    }

    @DisplayName("Test reservation creation when start date is after end date.")
    @Test
    public void StartDayAfterEndCreation(){
        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setPrice(300D);
        reservation.setTimeSlot(START_AFTER_END_SLOT);
        reservation.setGuest(null);
        reservation.setGuestNumber(4);
        reservation.setAccommodation(mockAccommodation);

        Throwable exception = assertThrows(ValidationException.class, () -> reservationService.create(reservation));
        assertEquals("Reservation start date is after end date.", exception.getMessage());
        verify(reservationRepository, times(0)).save(reservation);
    }



    @DisplayName("Test reservation creation when accommodation is not available.")
    @Test
    public void InvalidReservationCreation(){

        Reservation reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setPrice(300D);
        reservation.setTimeSlot(INVALID_SLOT);
        reservation.setGuest(null);
        reservation.setGuestNumber(3);
        reservation.setAccommodation(mockAccommodation);
        when(overlappingService.overlapsReservation(any(), any())).thenReturn(true);
        Throwable exception = assertThrows(ValidationException.class, () -> reservationService.create(reservation));
        assertEquals("Accommodation is not available for selected period.", exception.getMessage());
        verify(reservationRepository, times(0)).save(reservation);
    }
}
