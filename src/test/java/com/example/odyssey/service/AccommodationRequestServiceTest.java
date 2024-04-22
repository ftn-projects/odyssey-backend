package com.example.odyssey.service;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.exceptions.AvailabilitySlotsOverlappingException;
import com.example.odyssey.exceptions.InvalidAvailabilitySlotException;
import com.example.odyssey.exceptions.SlotHasReservationsException;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import com.example.odyssey.services.AccommodationRequestService;
import com.example.odyssey.services.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccommodationRequestServiceTest {
    @Mock
    private AccommodationRequestRepository requestRepository;
    @Mock
    private ReservationService reservationService;
    @Captor
    private ArgumentCaptor<AccommodationRequest> argumentCaptor;
    @InjectMocks
    private AccommodationRequestService service;

    AvailabilitySlot VALID_SLOT = new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.of(2024,Month.JUNE,13,0,0,0),
            LocalDateTime.of(2024,Month.JUNE,18,0,0,0)));

    @ParameterizedTest
    @MethodSource(value = "invalidSlots")
    @DisplayName("Should Throw Invalid Availability Slot Exception")
    public void shouldNotCreateAccommodationRequestInvalidAvailabilitySlot(AvailabilitySlot slot){
        Set<AvailabilitySlot> slots = new HashSet<>();
        slots.add(slot);

        AccommodationRequest request = data();
        request.getDetails().setNewAvailableSlots(slots);

        Throwable exception = assertThrows(InvalidAvailabilitySlotException.class, () -> service.create(request.getType(), request.getDetails(), new Host(), null));

        assertEquals("Invalid availability slot input.", exception.getMessage());
        verifyNoInteractions(requestRepository);
    }

    @Test
    @DisplayName("Should Throw Slot Has Reservations Exception")
    public void shouldNotCreateAccommodationRequestOverlappingReservation(){
        Set<AvailabilitySlot> slots = new HashSet<>();

        slots.add(VALID_SLOT);

        AccommodationRequest request = data();
        request.getDetails().setNewAvailableSlots(slots);

        when(reservationService.overlapsReservation(2L,VALID_SLOT.getTimeSlot())).thenReturn(true);

        Throwable exception = assertThrows(SlotHasReservationsException.class, () -> service.create(AccommodationRequest.Type.UPDATE, request.getDetails(), new Host(), 2L));

        assertEquals("Availability slot cannot be edited due to reservations made in that period.", exception.getMessage());
        verifyNoInteractions(requestRepository);
    }

    @ParameterizedTest
    @MethodSource(value = "overlappingSlots")
    @DisplayName("Should Throw Availability Slots Overlapping Exception")
    public void shouldNotCreateAccommodationRequestSlotsOverlapping(Set<AvailabilitySlot> slots){
        AccommodationRequest request = data();
        request.getDetails().setNewAvailableSlots(slots);

        Throwable exception = assertThrows(AvailabilitySlotsOverlappingException.class, () -> service.create(request.getType(), request.getDetails(), new Host(), null));

        assertEquals("Availability slots are overlapping.", exception.getMessage());
        verifyNoInteractions(requestRepository);
    }

    @Test
    @DisplayName("Should Create Accommodation Request")
    public void shouldCreateAccommodationRequest(){
        Set<AvailabilitySlot> slots = new HashSet<>();
        slots.add(VALID_SLOT);

        AccommodationRequest request = data();
        request.getDetails().setNewAvailableSlots(slots);

        service.create(request.getType(), request.getDetails(), new Host(), 2L);

        verify(requestRepository,times(1)).save(argumentCaptor.capture());
    }



    private static List<Arguments> invalidSlots(){
        return Arrays.asList(
                arguments(new AvailabilitySlot(-5D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                        LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0)))), //negative price
                arguments(new AvailabilitySlot(0D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                        LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0)))), //zero price
                arguments(new AvailabilitySlot(null, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                        LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0)))), //null price
                arguments(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                        LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0)))), //end and start dates are the same
                arguments(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.JANUARY, 5,0,0,0,0),
                        LocalDateTime.of(2024, Month.JANUARY, 2,0,0,0,0)))), //start date is before end date
                arguments(new AvailabilitySlot(500D, new TimeSlot( null, LocalDateTime.of(2024, Month.JANUARY, 2,0,0,0,0)))), //start date is null
                arguments(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.JANUARY, 1,0,0,0,0), null))) //end date is null
        );
    }

    private static List<Arguments> overlappingSlots(){
        return Arrays.asList(
                arguments(new HashSet<AvailabilitySlot>() {{
                    add(new AvailabilitySlot(300D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));
                    add(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));}}), //slots with same start and end date
                arguments(new HashSet<AvailabilitySlot>() {{
                    add(new AvailabilitySlot(300D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 8,0,0,0,0))));
                    add(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));}}), //slots with same start date
                arguments(new HashSet<AvailabilitySlot>() {{
                    add(new AvailabilitySlot(300D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));
                    add(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 2,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));}}), //slots with same end date
                arguments(new HashSet<AvailabilitySlot>() {{
                    add(new AvailabilitySlot(300D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 1,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 6,0,0,0,0))));
                    add(new AvailabilitySlot(500D, new TimeSlot( LocalDateTime.of(2024, Month.APRIL, 2,0,0,0,0),
                            LocalDateTime.of(2024, Month.APRIL, 4,0,0,0,0))));}}) //one slot is inside the other
        );
    }

    private AccommodationRequest data(){
        Set<Amenity> amenities = new HashSet<>();
        amenities.add(new Amenity(1L,"TV"));

        Set<String> images = new HashSet<>();
        images.add("tropical1.webp");

        Duration duration = Duration.between(LocalDateTime.now().plusDays(5), LocalDateTime.now());

        return new AccommodationRequest(null, LocalDateTime.now(), AccommodationRequest.Type.CREATE, AccommodationRequest.Status.REQUESTED,
                new AccommodationRequest.Details("title","description", Accommodation.Type.HOUSE, new Address("street","city","country"),
                        Accommodation.PricingType.PER_NIGHT, 300D, false, duration, null, amenities, 1,3,images ),
                new Host(), null );
    }
}
