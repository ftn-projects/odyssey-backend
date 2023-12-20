package com.example.odyssey.controllers;


import com.example.odyssey.dtos.accommodations.AccommodationRequestCreationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationRequestDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.mappers.AccommodationDTOMapper;
import com.example.odyssey.mappers.AccommodationRequestDTOMapper;
import com.example.odyssey.services.AccommodationRequestService;
import com.example.odyssey.services.UserService;
import com.example.odyssey.util.TokenUtil;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private AccommodationRequestService accommodationRequestService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenUtil tokenUtil;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findByStatus(@RequestParam AccommodationRequest.Status status) {
        List<AccommodationRequest> requests = accommodationRequestService.findByStatus(status);
        return new ResponseEntity<>(mapToDTO(requests), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam AccommodationRequest.Status status) {
        AccommodationRequest request = accommodationRequestService.findById(id);
        accommodationRequestService.editStatus(request, status);
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @PostMapping(value = "/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile image, @RequestHeader("Authorization") String authToken) throws IOException {
        try {
            AccommodationRequest request = accommodationRequestService.findById(id);
            tokenUtil.validateAccess(authToken, request.getHost().getId(), true);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.OK);
        }

        accommodationRequestService.uploadImage(id, image);
        return new ResponseEntity<>("Image uploaded successfully.", HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('HOST')")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AccommodationRequestCreationDTO dto) {
        AccommodationRequest.Details details = AccommodationDTOMapper.fromCreationDTOToAccommodationDetails(dto);
        details.setNewCancellationDue(Duration.ofDays(dto.getNewCancellationDue()));

        Host host = (Host) userService.find(dto.getHostId());
        AccommodationRequest request = accommodationRequestService.create(dto.getRequestType(), details, host, dto.getAccommodationId());
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request), HttpStatus.OK);
    }

    private static List<AccommodationRequestDTO> mapToDTO(List<AccommodationRequest> requests) {
        return requests.stream().map(AccommodationRequestDTO::new).toList();
    }
}
