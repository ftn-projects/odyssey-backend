package com.example.odyssey.controllers;


import com.example.odyssey.dtos.accommodations.AccommodationRequestDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.mappers.AccommodationRequestDTOMapper;
import com.example.odyssey.services.AccommodationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/accommodationRequests")
public class AccommodationRequestController {
    @Autowired
    private AccommodationRequestService service;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> findByStatus(@RequestParam AccommodationRequest.Status status) {
        List<AccommodationRequest> requests = service.findByStatus(status);
        return new ResponseEntity<>(mapToDTO(requests), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status){
        AccommodationRequest request = service.findById(id);
        service.editStatus(request,AccommodationRequest.Status.valueOf(status));
        return new ResponseEntity<>(AccommodationRequestDTOMapper.fromAccommodationRequestToDTO(request),HttpStatus.OK);
    }

    private static List<AccommodationRequestDTO> mapToDTO(List<AccommodationRequest> requests){
        return requests.stream().map(AccommodationRequestDTO::new).toList();
    }
}
