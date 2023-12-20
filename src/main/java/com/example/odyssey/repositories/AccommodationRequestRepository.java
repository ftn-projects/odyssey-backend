package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.AccommodationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccommodationRequestRepository extends JpaRepository<AccommodationRequest, Long> {
    List<AccommodationRequest> findAccommodationRequestByStatus(AccommodationRequest.Status status);
    AccommodationRequest findAccommodationRequestById(Long id);
}
