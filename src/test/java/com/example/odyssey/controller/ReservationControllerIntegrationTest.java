package com.example.odyssey.controller;

import com.example.odyssey.dtos.TimeSlotDTO;
import com.example.odyssey.dtos.reservations.ReservationRequestDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql({"/request-test.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int randomServerPort;

    private String token;

    @BeforeEach
    public void login() {
        SetupTests("milos@gmail.com","milos");
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


    @DisplayName("Create reservation unauthorized")
    @Test
    public void CreateReservationUnauthorizedTest() throws Exception{
        SetupTests("petar@gmail.com","petar");
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(3);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(VALID_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @DisplayName("Create reservation success")
    @Test
    public void CreateReservationSuccessTest() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(3);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(VALID_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @DisplayName("Create reservation when start date is after end date")
    @Test
    public void CreateReservationStartAfterEndSlotTest() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(3);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(START_AFTER_END_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Failed to create reservation: Reservation start date is after end date.", result.getBody());
    }


    @DisplayName("Create reservation when start date is in the past")
    @Test
    public void CreateReservationPastInvalidSlotTest() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(3);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(PAST_INVALID_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Failed to create reservation: Reservation start date is in the past.", result.getBody());
    }

    @DisplayName("Create reservation when accommodation is not available")
    @Test
    public void CreateReservationInvalidSlotTest() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(3);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(INVALID_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Failed to create reservation: Accommodation is not available for selected period.", result.getBody());
    }

    @DisplayName("Create reservation when guest number is invalid")
    @Test
    public void CreateReservationInvalidGuestNumberTest() throws Exception{
        ReservationRequestDTO reservationRequestDTO = new ReservationRequestDTO();
        reservationRequestDTO.setStatus(Reservation.Status.REQUESTED);
        reservationRequestDTO.setPrice(3000.0);
        reservationRequestDTO.setGuestNumber(10);
        reservationRequestDTO.setAccommodationId(1L);
        reservationRequestDTO.setRequestDate(LocalDateTime.now());
        reservationRequestDTO.setTimeSlot(new TimeSlotDTO(VALID_SLOT));
        reservationRequestDTO.setGuestId(7L);

        ResponseEntity<String> result = sendRequest(reservationRequestDTO);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Failed to create reservation: Reservation guest number is invalid.", result.getBody());
    }


    private ResponseEntity<String> sendRequest(ReservationRequestDTO dto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + randomServerPort + "/api/v1/reservations");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);

        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(dto, headers);


        try {
            return this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            System.out.println("Error during request: " + e.getMessage());
            throw e;
        }
    }


    private ResponseEntity<String> sendUnauthorizedRequest(ReservationRequestDTO dto) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + randomServerPort + "/api/v1/reservations");

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<ReservationRequestDTO> request = new HttpEntity<>(dto, headers);

        try {
            return this.restTemplate.exchange(builder.toUriString(), HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            System.out.println("Error during unauthorized request: " + e.getMessage());
            throw e;
        }
    }

    public void SetupTests(String name, String password){
        JSONObject body = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        try {
            body.put("username", name);
            body.put("password", password);

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("username", "password");
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + randomServerPort + "/api/v1/users/login");

            HttpEntity<?> entity = new HttpEntity<>(body.toString(), headers);

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
}
