package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.dtos.reservation.ReservationRequestDTO;
import com.example.odyssey.dtos.reservation.ReservationsAccreditDTO;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.mappers.ReservationDTOMapper;
import com.example.odyssey.mappers.ReservationRequestDTOMapper;
import com.example.odyssey.services.AccommodationService;
import com.example.odyssey.services.ReservationService;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private UserService userService;

    // GET method for getting all reservations
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = service.getAll();

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for accommodation
    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getByAccommodationId(@PathVariable Long id) {
        List<Reservation> reservations = service.findByAccommodation(id);

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for guest
    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/guest/{id}")
    public ResponseEntity<?> getByGuestId(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations;

        reservations = service.getFilteredByGuest(id, status, title, startDate, endDate);

        return new ResponseEntity<>(mapToAccreditDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for host
    @PreAuthorize("hasAuthority('HOST')")
    @GetMapping("/host/{id}")
    public ResponseEntity<?> getReservationsByHost(
            @PathVariable Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations;

        reservations = service.getFilteredByHost(id, status, title, startDate, endDate);

        return new ResponseEntity<>(mapToAccreditDTO(reservations), HttpStatus.OK);
    }

    // POST method for creating a reservation
    @PreAuthorize("hasAuthority('GUEST')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationRequestDTO requestDTO) {
        Reservation reservation;
        ReservationDTO dto;
        try {
            reservation = ReservationRequestDTOMapper.fromDTOtoReservation(requestDTO);
            reservation.setStatus(Reservation.Status.REQUESTED);
            reservation.setAccommodation(accommodationService.getOne(requestDTO.getAccommodationId()));
            reservation.setGuest((Guest) userService.findById(requestDTO.getGuestId()));
            reservation = service.create(reservation);
            service.automaticApproval(reservation);
            dto = ReservationDTOMapper.fromReservationToDTO(reservation);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // PUT method for updating a reservation status
    @PreAuthorize("hasAuthority('HOST') || hasAuthority('GUEST')")
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Reservation reservation = service.find(id);
        service.updateStatus(reservation, status);
        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.OK);
    }

    private static List<ReservationDTO> mapToDTO(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDTO::new).toList();
    }

    private List<ReservationsAccreditDTO> mapToAccreditDTO(List<Reservation> reservations){
        return reservations.stream().map(r->new ReservationsAccreditDTO(r, service.getCancellationNumber(r.getGuest().getId()))).toList();
    }
}