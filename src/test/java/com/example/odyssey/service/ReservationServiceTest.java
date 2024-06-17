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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.odyssey.entity.reservations.Reservation.Status.*;
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

    static Stream<Arguments> provideReservationsForOverlapTests() {
        return Stream.of(
                /* Test cases for overlapping reservations */
                Arguments.of(
                        getTimeSlot("2024/06/19", "2024/06/22"),
                        List.of(getTimeSlot("2024/06/21", "2024/06/24")),
                        List.of(DECLINED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/25", "2024/06/28"),
                        List.of(getTimeSlot("2024/06/22", "2024/06/26")),
                        List.of(DECLINED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/19", "2024/06/22"),
                        List.of(getTimeSlot("2024/06/21", "2024/06/25"), getTimeSlot("2024/06/20", "2024/06/25")),
                        List.of(DECLINED, DECLINED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/25", "2024/06/28"),
                        List.of(getTimeSlot("2024/06/22", "2024/06/26"), getTimeSlot("2024/06/22", "2024/06/27")),
                        List.of(DECLINED, DECLINED)
                ),
                /* Test cases for some overlapping reservations (not all) */
                Arguments.of(
                        getTimeSlot("2024/06/25", "2024/06/28"),
                        List.of(getTimeSlot("2024/06/22", "2024/06/23"), getTimeSlot("2024/06/22", "2024/06/27")),
                        List.of(REQUESTED, DECLINED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/19", "2024/06/22"),
                        List.of(getTimeSlot("2024/06/20", "2024/06/23"), getTimeSlot("2024/06/23", "2024/06/27")),
                        List.of(DECLINED, REQUESTED)
                ),
                /* Test cases for non-overlapping reservations */
                Arguments.of(
                        getTimeSlot("2024/06/19", "2024/06/22"),
                        List.of(getTimeSlot("2024/06/22", "2024/06/24")),
                        List.of(REQUESTED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/25", "2024/06/28"),
                        List.of(getTimeSlot("2024/06/22", "2024/06/25")),
                        List.of(REQUESTED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/19", "2024/06/22"),
                        List.of(getTimeSlot("2024/06/23", "2024/06/27"), getTimeSlot("2024/06/23", "2024/06/26")),
                        List.of(REQUESTED, REQUESTED)
                ),
                Arguments.of(
                        getTimeSlot("2024/06/25", "2024/06/28"),
                        List.of(getTimeSlot("2024/06/21", "2024/06/24"), getTimeSlot("2024/06/29", "2024/06/30")),
                        List.of(REQUESTED, REQUESTED)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideReservationsForOverlapTests")
    void shouldUpdateStatusesForOverlappingReservations(TimeSlot acceptingTimeSlot, List<TimeSlot> reservationTimeSlots, List<Reservation.Status> expectedStatuses) {
        Long accommodationId = 1L;
        var accommodation = new Accommodation();
        accommodation.setId(accommodationId);

        var reservation = new Reservation();
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setAccommodation(accommodation);
        reservation.setTimeSlot(acceptingTimeSlot);

        var reservations = reservationTimeSlots.stream().map(slot -> {
            var r = new Reservation();
            r.setStatus(REQUESTED);
            r.setAccommodation(accommodation);
            r.setTimeSlot(slot);
            return r;
        }).collect(Collectors.toList());

        when(reservationRepository.findById(eq(1L))).thenReturn(Optional.of(reservation));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        when(reservationRepository.findReservationsByAccommodation_Id(accommodationId)).thenReturn(reservations);

        Reservation acceptedReservation = service.accept(1L);

        assertEquals(ACCEPTED, acceptedReservation.getStatus());
        for (int i = 0; i < reservations.size(); ++i)
            assertEquals(expectedStatuses.get(i), reservations.get(i).getStatus());
        verify(reservationRepository, times(countDeclined(expectedStatuses) + 1)).save(any(Reservation.class));
    }

    private static TimeSlot getTimeSlot(String start, String end) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return new TimeSlot(
                LocalDate.parse(start, formatter).atStartOfDay(),
                LocalDate.parse(end, formatter).atStartOfDay()
        );
    }

    private static int countDeclined(List<Reservation.Status> statuses) {
        return (int) statuses.stream().filter(status -> status.equals(DECLINED)).count();
    }
}

