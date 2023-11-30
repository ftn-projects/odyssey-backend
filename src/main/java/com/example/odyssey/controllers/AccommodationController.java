package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDetailsDTO;
import com.example.odyssey.dtos.accommodations.AccommodationSearchDTO;
import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
//    @Autowired
//    private AccommodationService service;

//    @Autowired
//    public AccommodationController(AccommodationService service) {
//        this.service = service;
//    }

    private final List<Accommodation> data = DummyData.getAccommodations();

    @GetMapping
    public ResponseEntity<List<AccommodationSearchDTO>> getAll(
            @RequestParam String search,
            @RequestParam Long dateStart,
            @RequestParam Long dateEnd,
            @RequestParam Integer guestNumber,
            @RequestParam(required = false) List<Long> amenities,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double priceStart,
            @RequestParam(required = false) Double priceEnd
    ) {
        List<Accommodation> accommodations = data.subList(0, 3);

        // accommodations = service.searchAll(search, dateStart, dateEnd, guestNumber);
        // accommodations = service.filter(accommodations, amenities, type, priceStart, priceEnd)

        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDetailsDTO> findById(@PathVariable Long id) {
        Accommodation accommodation = data.stream()
                .filter((a) -> Objects.equals(a.getId(), id))
                .findFirst().orElse(new Accommodation());

        // accommodation = service.findById(id);

        return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
    }

    @GetMapping("/favorites/{id}")
    public ResponseEntity<List<AccommodationSearchDTO>> findByGuestFavorites(@PathVariable Long id) {
        List<Accommodation> accommodations = data.subList(3, 5);

        // accommodation = service.findByGuestFavorites(id);

        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    private static List<AccommodationSearchDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map((a) -> new AccommodationSearchDTO(a, 1000.0, 0.0)).toList();
    }
}
