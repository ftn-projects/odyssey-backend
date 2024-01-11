package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.statistics.AccommodationStatDTO;
import com.example.odyssey.dtos.statistics.HostStatDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.services.AccommodationService;
import com.example.odyssey.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService service;

    @Autowired
    private ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) List<Long> amenities,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double priceStart,
            @RequestParam(required = false) Double priceEnd
    ) {
        List<Accommodation> accommodations;
        accommodations = service.getAll(location, dateStart, dateEnd, guestNumber, amenities, type, priceStart, priceEnd);
        List<AccommodationDTO> AccommodationDTOs = mapToDTO(accommodations);
        for (AccommodationDTO accommodationDTO : AccommodationDTOs) {
            accommodationDTO.setTotalPrice(service.calculateTotalPrice(accommodationDTO.getId(), dateStart, dateEnd, guestNumber));
            accommodationDTO.setDefaultPrice(service.getPriceForDateRange(accommodationDTO.getId(), dateStart, dateEnd));
            accommodationDTO.setAverageRating(reviewService.getTotalRatingByAccommodation(accommodationDTO.getId()));
        }
        return new ResponseEntity<>(AccommodationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDTO> findById(
            @PathVariable Long id
    ) {
        Accommodation accommodation = service.getOne(id);
        AccommodationDTO accommodationDTO = new AccommodationDTO(accommodation);

        if (accommodation != null)
            return new ResponseEntity<>(new AccommodationDTO(accommodation), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/totalPrice")
    public ResponseEntity<AccommodationDTO> findByDateAndGuests(
            @PathVariable Long id,
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber
    ) {
        Accommodation accommodation = service.getOne(id);
        AccommodationDTO accommodationDTO = new AccommodationDTO(accommodation);

        if (accommodation != null) {
            accommodationDTO.setTotalPrice(service.calculateTotalPrice(accommodation.getId(), dateStart, dateEnd, guestNumber));
            accommodationDTO.setDefaultPrice(service.getPriceForDateRange(accommodation.getId(), dateStart, dateEnd));

            return new ResponseEntity<>(accommodationDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('GUEST')")
    @GetMapping("/favorites/{id}")
    public ResponseEntity<?> findByGuestFavorites(@PathVariable Long id) {
        List<Accommodation> accommodations = new ArrayList<>();


        // accommodation = service.findByGuestFavorites(id);


        return new ResponseEntity<>(mapToDTO(accommodations), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id, @PathVariable String imageName) throws IOException {
        return new ResponseEntity<>(service.getImage(id, imageName), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images")
    public ResponseEntity<?> getImages(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(service.getImageNames(id), HttpStatus.OK);
    }

    @GetMapping(value = "/amenities")
    public ResponseEntity<?> getAmenities() {
        return new ResponseEntity<>(service.getAmenities(), HttpStatus.OK);
    }

    @GetMapping(value = "/stats/period")
    public ResponseEntity<?> getPeriodStats(@RequestParam Long startDate, @RequestParam Long endDate) {
        List<HostStatDTO> statistics = new ArrayList<>();
        statistics.add(new HostStatDTO());
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    @GetMapping(value = "/stats/{id}")
    public ResponseEntity<?> getAccommodationStats(@PathVariable Long id, @RequestParam(required = false) Long year) {
        List<AccommodationStatDTO> statistics = new ArrayList<>();
        statistics.add(new AccommodationStatDTO());
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }

    private static List<AccommodationDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map(AccommodationDTO::new).toList();
    }
}
