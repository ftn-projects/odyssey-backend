package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccommodationRequestService {
    @Autowired
    AccommodationRequestRepository repository;
    @Autowired
    AccommodationService service;
    @Autowired
    AccommodationRequestService requestService;
    public List<AccommodationRequest> findByStatus (AccommodationRequest.Status status) {return repository.findAccommodationRequestByStatus(status);}
    public AccommodationRequest findById (Long id) {return repository.findAccommodationRequestById(id);}
    public void editStatus(AccommodationRequest request, AccommodationRequest.Status status){
        request.setStatus(status);
        Accommodation accommodation;
        if(status.equals(AccommodationRequest.Status.ACCEPTED)){
            if(request.getType().equals(AccommodationRequest.Type.CREATE)){
                accommodation = new Accommodation(request.getDetails());
                accommodation.setHost(request.getHost());
                service.save(accommodation);
            }
            else{ service.editAccommodation(request.getAccommodationId(),request.getDetails());}
        }
        save(request);
    }

    public void save(AccommodationRequest request) {repository.save(request);}
}
