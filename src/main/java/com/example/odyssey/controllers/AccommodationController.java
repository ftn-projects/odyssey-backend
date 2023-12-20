package com.example.odyssey.controllers;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.accommodations.AccommodationCreationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDetailsDTO;
import com.example.odyssey.dtos.accommodations.AmenityDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
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
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/accommodations")
public class AccommodationController {
    @Autowired
    private AccommodationService service;
    @Autowired
    private UserService userService;


//    @Autowired
//    public AccommodationController(AccommodationService service) {
//        this.service = service;
//    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) Long dateStart,
            @RequestParam(required = false) Long dateEnd,
            @RequestParam(required = false) Integer guestNumber,
            @RequestParam(required = false) List<Long> amenities,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) Double priceStart,
            @RequestParam(required = false) Double priceEnd
    ) {
        List<Accommodation> accommodations;
        accommodations = service.getAll(dateStart, dateEnd, guestNumber, amenities, type, priceStart, priceEnd);
        List<AccommodationDTO> AccommodationDTOs = mapToDTO(accommodations);
        for (AccommodationDTO accommodationDTO : AccommodationDTOs) {
            accommodationDTO.setTotalPrice(service.calculateTotalPrice(accommodationDTO.getId(), dateStart, dateEnd, guestNumber));
        }
        return new ResponseEntity<>(AccommodationDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccommodationDetailsDTO> findById(@PathVariable Long id) {
        Accommodation accommodation = service.getOne(id);

        if (accommodation != null)
            return new ResponseEntity<>(new AccommodationDetailsDTO(accommodation), HttpStatus.OK);
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
    public ResponseEntity<?> getAmenities() {
        return new ResponseEntity<>(service.getAmenities(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccommodationCreationDTO dto) {
        Accommodation accommodation = AccommodationDTOMapper.fromCreationDTOToAccommodation(dto);

        accommodation.setHost((Host) userService.find(dto.getHostId()));
        accommodation = service.create(accommodation, dto.getAmenities().stream().map(AmenityDTO::getId).toList());

        return new ResponseEntity<>(AccommodationDTOMapper.fromAccommodationToDTO(accommodation), HttpStatus.OK);
    }

    private static List<AccommodationDTO> mapToDTO(List<Accommodation> accommodations) {
        return accommodations.stream().map((a) -> new AccommodationDTO(a)).toList();
    }
}
