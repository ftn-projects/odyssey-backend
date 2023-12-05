package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.mappers.ReservationDTOMapper;
import com.example.odyssey.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@RestController
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;

    // GET method for getting all reservations
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = service.getAll();

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for accommodation
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getByAccommodationId(@PathVariable Long id) {
        List<Reservation> reservations = service.findByAccommodation(id);

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for guest
    @GetMapping("/guest/{id}")
    public ResponseEntity<?> getByGuestId(
            @PathVariable Long id,
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations =  service.findByGuest(id);

        reservations = service.filter(reservations, accommodationId, Reservation.Status.valueOf(status),
                service.convertToDate(startDate), service.convertToDate(endDate));

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for host
    @GetMapping("/host/{id}")
    public ResponseEntity<?> getReservationsByHost(
            @PathVariable Long id,
            @RequestParam(required = false) Long accommodationId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations = service.findByHost(id);

        reservations = service.filter(reservations, accommodationId, Reservation.Status.valueOf(status),
                service.convertToDate(startDate), service.convertToDate(endDate));

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // POST method for creating a reservation
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = ReservationDTOMapper.fromDTOtoReservation(reservationDTO);

        reservation = service.save(reservation);

        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.CREATED);
    }

    // PUT method for updating a reservation status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Reservation reservation = service.find(id);
        reservation.setStatus(Reservation.Status.valueOf(status));
        reservation = service.save(reservation);

        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.OK);
    }

    private static List<ReservationDTO> mapToDTO(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDTO::new).toList();
    }
}