package com.example.odyssey.controllers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDetailsDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.mappers.AccommodationDTOMapper;
import com.example.odyssey.services.AccommodationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
        @Autowired
        private AccommodationService service;


//    @Autowired
//    public AccommodationController(AccommodationService service) {
//        this.service = service;
//    }

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
        List<Accommodation> accommodations;
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
        List<Accommodation> accommodations = new ArrayList<>();;

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
