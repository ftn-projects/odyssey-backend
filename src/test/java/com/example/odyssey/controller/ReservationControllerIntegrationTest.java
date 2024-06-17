package com.example.odyssey.controller;

import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.services.NotificationService;
import com.example.odyssey.services.ReservationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private NotificationService notificationService;

    @LocalServerPort
    private int randomServerPort;

    private String token;

    @BeforeAll
    public void login() {
        JSONObject body = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        try {
            body.put("username", "petar@gmail.com");
            body.put("password", "petar");

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

    @Test
    @DisplayName("Should respond with Not Found When making PUT request with invalid reservation id")
    public void shouldRespondWithNotFoundForInvalidId() {
        Long id = 99L;

        ResponseEntity<String> result = sendRequest(id);
        String errorMessage = result.getBody();

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertEquals("Reservation with id 99 not found.", errorMessage);
    }

    @Test
    @DisplayName("Should respond with Unprocessable Entity When making PUT request with already accepted reservation")
    public void shouldRespondWithBadRequestForAlreadyAcceptedReservation() {
        Long id = 1L;

        ResponseEntity<String> result = sendRequest(id);
        String errorMessage = result.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Reservation has already been accepted.", errorMessage);
    }

    @Test
    @DisplayName("Should respond with Unprocessable Entity When making PUT request with reservation that is not requested")
    public void shouldRespondWithBadRequestForReservationThatIsNotRequested() {
        Long id = 2L;

        ResponseEntity<String> result = sendRequest(id);
        String errorMessage = result.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("Reservation status is not requested.", errorMessage);
    }

    @Test
    @DisplayName("Should respond with Unprocessable Entity When making PUT request with reservation that has already started")
    public void shouldRespondWithOkWhenReservationIsAccepted() {
        Long id = 3L;

        ResponseEntity<String> result = sendRequest(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(notificationService, times(1)).notifyAccepted(any());
    }


    private ResponseEntity<String> sendRequest(Long id) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + randomServerPort + "/api/v1/reservations/accept/" + id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + this.token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            return this.restTemplate.exchange(builder.toUriString(), HttpMethod.PUT, request, String.class);
        } catch (Exception e) {
            System.out.println("Error during request: " + e.getMessage());
            throw e;
        }
    }
}
