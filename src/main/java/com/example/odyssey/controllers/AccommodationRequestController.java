package com.example.odyssey.controllers;


import com.example.odyssey.dtos.accommodations.AccommodationRequestCreationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationRequestDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.mappers.AccommodationDTOMapper;
import com.example.odyssey.mappers.AccommodationRequestDTOMapper;
import com.example.odyssey.services.AccommodationRequestService;
import com.example.odyssey.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accommodationRequests")
public class AccommodationRequestController {
    @Autowired
    private AccommodationRequestService service;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findByStatus(@RequestParam AccommodationRequest.Status status) {
        List<AccommodationRequest> requests = service.findByStatus(status);
        return new ResponseEntity<>(mapToDTO(requests), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        AccommodationRequest request = service.findById(id);
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request), HttpStatus.OK);
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam AccommodationRequest.Status status) throws IOException {
        AccommodationRequest request = service.findById(id);
        service.editStatus(request, status);
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id, @PathVariable String imageName) throws IOException {
        return new ResponseEntity<>(service.getImage(id, imageName), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/images")
    public ResponseEntity<?> getImages(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(service.getImageNames(id), HttpStatus.OK);
    }

    @PostMapping(value = "/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image) throws IOException {
        service.uploadImage(id, image);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AccommodationRequestCreationDTO dto) throws IOException {
        AccommodationRequest.Details details = AccommodationDTOMapper.fromCreationDTOToAccommodationDetails(dto);
        details.setNewCancellationDue(Duration.ofDays(dto.getNewCancellationDue()));

        Host host = (Host) userService.findById(dto.getHostId());
        AccommodationRequest request = service.create(dto.getRequestType(), details, host, dto.getAccommodationId());
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request), HttpStatus.CREATED);
    }

    private static List<AccommodationRequestDTO> mapToDTO(List<AccommodationRequest> requests) {
        return requests.stream().map(AccommodationRequestDTO::new).toList();
    }
}
