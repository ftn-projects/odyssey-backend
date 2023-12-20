package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationCreationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.mappers.AccommodationDTOMapper;
import com.example.odyssey.services.AccommodationService;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService service;
    @Autowired
    private UserService userService;

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
        }
        return new ResponseEntity<>(AccommodationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDTO> findById(@PathVariable Long id) {
        Accommodation accommodation = service.getOne(id);

        if (accommodation != null)
            return new ResponseEntity<>(new AccommodationDTO(accommodation), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/favorites/{id}")
    public ResponseEntity<?> findByGuestFavorites(@PathVariable Long id) {
        List<Accommodation> accommodations = new ArrayList<>();
        ;

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
    public ResponseEntity<?> getAmenities() throws IOException {
        return new ResponseEntity<>(service.getAmenities(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccommodationCreationDTO accommodationDTO) {
        Accommodation accommodation = new Accommodation();

        accommodation.setTitle(accommodationDTO.getTitle());
        accommodation.setType(accommodationDTO.getType());
        accommodation.setHost((Host) userService.find(accommodationDTO.getHost()));
        accommodation.setPricing(accommodationDTO.getPricing());
        accommodation.setAutomaticApproval(accommodationDTO.getAutomaticApproval());
        accommodation.setCancellationDue(accommodationDTO.getCancellationDue());
        accommodation.setMinGuests(accommodationDTO.getMinGuests());
        accommodation.setMaxGuests(accommodationDTO.getMaxGuests());
        accommodation.setDescription(accommodationDTO.getDescription());
        accommodation.setDefaultPrice(accommodationDTO.getDefaultPrice());
        accommodation.setAmenities(new HashSet<>(
                accommodationDTO.getAmenities().stream().map((a) -> new Amenity(a.getId(), a.getTitle())).toList())
        );
        accommodation.setAvailableSlots(new HashSet<>(
                accommodationDTO.getAvailableSlots().stream().map((s) -> new AvailabilitySlot(
                        s.getPrice(), new TimeSlot(s.getTimeSlot().getStart(), s.getTimeSlot().getEnd()))
                ).toList())
        );


        accommodation = service.create(accommodation);

        return new ResponseEntity<>(accommodation, HttpStatus.OK);
    }

    private static List<AccommodationDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map((a) -> new AccommodationDTO(a)).toList();
    }
}
