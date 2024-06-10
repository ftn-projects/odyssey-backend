package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reservations.ReservationDTO;
import com.example.odyssey.dtos.reservations.ReservationRequestDTO;
import com.example.odyssey.dtos.reservations.ReservationsAccreditDTO;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.mappers.ReservationDTOMapper;
import com.example.odyssey.mappers.ReservationRequestDTOMapper;
import com.example.odyssey.services.AccommodationService;
import com.example.odyssey.services.NotificationService;
import com.example.odyssey.services.ReservationService;
import com.example.odyssey.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {
    @Autowired
    private ReservationService service;
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = service.getAll();

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getByAccommodationId(@PathVariable Long id) {
        List<Reservation> reservations = service.findByAccommodation(id);

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    @GetMapping("/guest/{id}")
    public ResponseEntity<?> getByGuestId(
            @PathVariable String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations;

        reservations = service.getFilteredByGuest(id, status, title, startDate, endDate);

        return new ResponseEntity<>(mapToAccreditDTO(reservations), HttpStatus.OK);
    }

    @GetMapping("/host/{id}")
    public ResponseEntity<?> getReservationsByHost(
            @PathVariable String id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) List<String> status,
            @RequestParam(required = false) Long startDate,
            @RequestParam(required = false) Long endDate) {
        List<Reservation> reservations;

        reservations = service.getFilteredByHost(id, status, title, startDate, endDate);

        return new ResponseEntity<>(mapToAccreditDTO(reservations), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ReservationRequestDTO requestDTO) {
        Reservation reservation = ReservationRequestDTOMapper.fromDTOtoReservation(requestDTO);
        reservation.setStatus(Reservation.Status.REQUESTED);
        reservation.setAccommodation(accommodationService.findById(requestDTO.getAccommodationId()));
        reservation.setGuest((Guest) userService.findById(requestDTO.getGuestId()));
        reservation = service.create(reservation);
        service.automaticApproval(reservation);

        notificationService.notifyRequested(reservation);
        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.CREATED);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Reservation reservation = service.find(id);
        service.updateStatus(reservation, status);

        if (reservation.getStatus().equals(Reservation.Status.CANCELLED_RESERVATION))
            notificationService.notifyCancelled(reservation);
        else if (reservation.getStatus().equals(Reservation.Status.ACCEPTED))
            notificationService.notifyAccepted(reservation);
        else if (reservation.getStatus().equals(Reservation.Status.DECLINED))
            notificationService.notifyDeclined(reservation);

        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.OK);
    }

    private static List<ReservationDTO> mapToDTO(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDTO::new).toList();
    }

    private List<ReservationsAccreditDTO> mapToAccreditDTO(List<Reservation> reservations) {
        return reservations.stream().map(r -> new ReservationsAccreditDTO(r, service.getCancellationNumber(r.getGuest().getId()))).toList();
    }
}