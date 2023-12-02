package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reservation.ReservationDTO;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.mappers.ReservationDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/reservations")
public class ReservationController {
//    @Autowired
//    private ReservationService service;
//
//    @Autowired
//    public ReservationController(ReservationService service) {
//        this.service = service;
//    }

    private final List<Reservation> data = DummyData.getReservations();

    // GET method for getting all reservations
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = data;

//        reservations = service.getAll();

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // GET method for getting all reservations for accommodation
    @GetMapping("/accommodation/{id}")
    public ResponseEntity<?> getByAccommodationId(@PathVariable Long id) {
        List<Reservation> reservations = data.subList(0, 3);

//        reservations = service.getByAccommodationId(id);

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
        List<Reservation> reservations = data.subList(2, 4);

//        reservations = service.getByGuestId(id);
//        reservations = service.filter(reservations, accommodationId, status, startDate, endDate);

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
        List<Reservation> reservations = data.subList(1, 4);

//        reservations = service.getByHostId(id);
//        reservations = service.filter(reservations, accommodationId, status, startDate, endDate);

        return new ResponseEntity<>(mapToDTO(reservations), HttpStatus.OK);
    }

    // POST method for creating a reservation
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = ReservationDTOMapper.fromDTOtoReservation(reservationDTO);

//        reservation = service.create(reservation);

        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.CREATED);
    }

    // PUT method for updating a reservation status
    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        Reservation reservation = data.stream().filter((r) -> Objects.equals(r.getId(), id))
                .findFirst().orElse(new Reservation());

//        reservation = service.getById(id);
        reservation.setStatus(Reservation.Status.valueOf(status));
//        reservation = service.update(reservation);

        return new ResponseEntity<>(ReservationDTOMapper.fromReservationToDTO(reservation), HttpStatus.OK);
    }

    private static List<ReservationDTO> mapToDTO(List<Reservation> reservations) {
        return reservations.stream().map(ReservationDTO::new).toList();
    }
}