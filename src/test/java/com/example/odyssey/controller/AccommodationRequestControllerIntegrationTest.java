package com.example.odyssey.controller;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.accommodations.AccommodationRequestCreationDTO;
import com.example.odyssey.dtos.accommodations.AmenityDTO;
import com.example.odyssey.dtos.accommodations.AvailabilitySlotDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccommodationRequestControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;
    private String token;

    @BeforeAll
    public void login(){
        JSONObject body = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        try{
            body.put("username","petar@gmail.com");
            body.put("password", "petar");

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("username","password");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://localhost:" + randomServerPort + "/api/v1/users/login");

            HttpEntity<?> entity = new HttpEntity<>(body.toString(),headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.POST,
                    entity,
                    String.class);

            JSONObject responseBody = new JSONObject(response.getBody());
            token = responseBody.getString("token");

        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Should Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests")
    public void shouldCreateRequest(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Invalid Default Price")
    public void shouldNotCreateRequestInvalidPrice(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(-5D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newDefaultPrice");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Default price must be positive.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due Default Price being Empty")
    public void shouldNotCreateRequestEmptyPrice(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(null);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newDefaultPrice");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Default price must be set.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due Default Price being Zero")
    public void shouldNotCreateRequestZeroPrice(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(0D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newDefaultPrice");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Default price must be positive.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Invalid Cancellation Due")
    public void shouldNotCreateRequestBadCancellationDue(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(-5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newCancellationDue");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Cancellation due must be positive.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Cancellation Due being Empty")
    public void shouldNotCreateRequestEmptyCancellationDue(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(null);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newCancellationDue");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Cancellation due must be set.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Cancellation Due being Zero")
    public void shouldNotCreateRequestZeroCancellationDue(){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5)))));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(0L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newCancellationDue");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Cancellation due must be positive.");
    }

    @ParameterizedTest
    @MethodSource(value = "invalidSlots")
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Invalid Availability Slots")
    public void shouldNotCreateRequestInvalidAvailabilitySlots(AvailabilitySlot slot){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(slot));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newAvailableSlots");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Invalid availability slot input.");
    }

    @Test
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Availability Slots being Empty")
    public void shouldNotCreateRequestEmptyAvailabilitySlots(){
        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(null);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newAvailableSlots");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Available slots must be set.");
    }

    @ParameterizedTest
    @MethodSource(value = "slotsOverlappingReservation")
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Availability Slots Overlapping With Reservations")
    public void shouldNotCreateRequestAvailabilitySlotsOverlapReservations(AvailabilitySlot slot){
        Set<AvailabilitySlotDTO> slots = new HashSet<>();
        slots.add(new AvailabilitySlotDTO(slot));

        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(5L);
        creationDTO.setRequestType(AccommodationRequest.Type.UPDATE);
        creationDTO.setAccommodationId(1L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newAvailableSlots");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Availability slot cannot be edited due to reservations made in that period.");
    }

    @ParameterizedTest
    @MethodSource(value = "overlappingSlots")
    @DisplayName("Should Not Create Accommodation Request When making POST request to endpoint /api/v1/accommodationRequests Due To Availability Slots Overlapping With Each Other")
    public void shouldNotCreateRequestAvailabilitySlotsOverlapEachOther(Set<AvailabilitySlotDTO> slots){
        AccommodationRequestCreationDTO creationDTO = data();
        creationDTO.setNewAvailableSlots(slots);
        creationDTO.setNewDefaultPrice(300D);
        creationDTO.setNewCancellationDue(5L);

        ResponseEntity<String> result = getResult(creationDTO);

        String errorMessage = "";
        try{
            JSONObject responseBody = new JSONObject(result.getBody());
            errorMessage = responseBody.getString("newAvailableSlots");
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, result.getStatusCode());
        assertEquals(errorMessage, "Availability slots are overlapping.");
    }

    private AccommodationRequestCreationDTO data(){
        Set<AmenityDTO> amenities = new HashSet<>();
        amenities.add(new AmenityDTO(new Amenity(1L,"TV")));

        Set<String> images = new HashSet<>();
        images.add("tropical1.webp");

        return new AccommodationRequestCreationDTO(
                -1L, AccommodationRequest.Type.CREATE, "title", "description", Accommodation.Type.APARTMENT,
                new AddressDTO("street","city","country"), Accommodation.PricingType.PER_NIGHT, null,
                false, null, null, amenities, 2,5, images, 2L, null);
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

    private static List<Arguments> slotsOverlappingReservation(){
        return Arrays.asList(
                arguments(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.of(2024,Month.JUNE,13,0,0,0),
                                LocalDateTime.of(2024,Month.JUNE,18,0,0,0)))), //same timeslot
                arguments(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.of(2024,Month.JUNE,13,0,0,0),
                                LocalDateTime.of(2024,Month.JUNE,20,0,0,0)))), //same start date
                arguments(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.of(2024,Month.JUNE,10,0,0,0),
                                LocalDateTime.of(2024,Month.JUNE,18,0,0,0)))), //same end date
                arguments(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.of(2024,Month.JUNE,10,0,0,0),
                        LocalDateTime.of(2024,Month.JUNE,20,0,0,0)))) //reservation fully inside timeslot
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

    private ResponseEntity<String> getResult(AccommodationRequestCreationDTO creationDTO){
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://localhost:" + randomServerPort + "/api/v1/accommodationRequests");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + this.token);

        HttpEntity<AccommodationRequestCreationDTO> request = new HttpEntity<>(creationDTO,headers);

        return this.restTemplate.postForEntity( builder.toUriString(),request, String.class);
    }
}