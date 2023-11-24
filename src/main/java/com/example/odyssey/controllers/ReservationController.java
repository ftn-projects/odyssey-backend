package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reservation.ResponseReservationDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {

    // GET method for getting all reservations
    @GetMapping
    public ResponseEntity<List<ResponseReservationDTO>> getAllReservations() {
        List<ResponseReservationDTO> reservations = null;
        // ...
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // GET method for getting all reservations for accommodation
    @GetMapping("/accommodation/{accommodationId}")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByAccommodation(@PathVariable Long accommodationId) {
        List<ResponseReservationDTO> reservations = null;
        // ...
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // GET method for getting all reservations for guest
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByGuest(
            @PathVariable Long guestId,
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<ResponseReservationDTO> reservations = null;
        // ...
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // GET method for getting all reservations for host
    @GetMapping("/host/{hostId}")
    public ResponseEntity<List<ResponseReservationDTO>> getReservationsByHost(
            @PathVariable Long guestId,
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        List<ResponseReservationDTO> reservations = null;
        // ...
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // POST method for creating a reservation
    @PostMapping
    public ResponseEntity<ResponseReservationDTO> createReservation(@RequestBody ResponseReservationDTO reservationDTO) {
        // ...
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    // PUT method for updating a reservation status
    @PutMapping("/{reservationId}/status")
    public ResponseEntity<ResponseReservationDTO> updateReservationStatus(
            @PathVariable Long reservationId,
            @RequestParam String status
    ) {
        // ...
        ResponseReservationDTO updatedReservation = null;
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }
}