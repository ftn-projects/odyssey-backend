package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDetailsDTO;
import com.example.odyssey.dtos.accommodations.AccommodationSearchDTO;
import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.AccommodationDTOMapper;
import com.example.odyssey.services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
        @Autowired
        private AccommodationService service;

//    @Autowired
//    public AccommodationController(AccommodationService service) {
//        this.service = service;
//    }

    private final List<Accommodation> data = DummyData.getAccommodations();

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) List<Amenity> amenities,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double priceStart,
            @RequestParam(required = false) Double priceEnd
    ) {
        List<Accommodation> accommodations = data.subList(0, 3);
        accommodations = service.getAll(dateStart, dateEnd, guestNumber, amenities, type, priceStart, priceEnd);

        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDetailsDTO> findById(@PathVariable Long id) {
        Accommodation accommodation = service.getOne(id);

        if (accommodation != null)
            return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/favorites/{id}")
    public ResponseEntity<?> findByGuestFavorites(@PathVariable Long id) {
        List<Accommodation> accommodations = data.subList(1, 3);

        // accommodation = service.findByGuestFavorites(id);

        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccommodationDTO accommodationDTO) {
        Accommodation accommodation = AccommodationDTOMapper.fromDTOToAccommodation(accommodationDTO);
         accommodation = service.save(accommodation);

        return new ResponseEntity<>(AccommodationDTOMapper.fromAccommodationToDTO(accommodation), HttpStatus.OK);
    }

    private static List<AccommodationDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map((a) -> new AccommodationDTO(a)).toList();
    }
}
