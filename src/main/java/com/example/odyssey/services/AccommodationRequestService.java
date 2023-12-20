package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import com.example.odyssey.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccommodationRequestService {
    @Autowired
    private AccommodationRequestRepository repository;
    @Autowired
    private AccommodationService accommodationService;

    private final String imagesDirPath = "src/main/resources/images/accommodationRequests/";

    public List<AccommodationRequest> findByStatus (AccommodationRequest.Status status) {return repository.findAccommodationRequestByStatus(status);}
    public AccommodationRequest findById (Long id) {return repository.findAccommodationRequestById(id);}
    public void editStatus(AccommodationRequest request, AccommodationRequest.Status status){
        request.setStatus(status);
        Accommodation accommodation;
        if(status.equals(AccommodationRequest.Status.ACCEPTED)){
            if(request.getType().equals(AccommodationRequest.Type.CREATE)){
                accommodation = new Accommodation(request.getDetails());
                accommodation.setHost(request.getHost());
                accommodationService.save(accommodation);
            }
            else{ accommodationService.editAccommodation(request.getAccommodationId(),request.getDetails());}
        }
        repository.save(request);
    }

    public AccommodationRequest create(AccommodationRequest.Type type, AccommodationRequest.Details details, Host host, Long accommodationId) {
        AccommodationRequest request = new AccommodationRequest(
                -1L, LocalDateTime.now(), type, AccommodationRequest.Status.REQUESTED, details, host, accommodationId);
        return repository.save(request);
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        if (image.getOriginalFilename() == null)
            throw new IOException("Image is non existing.");
        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);
        ImageUploadUtil.saveImage(uploadDir, image.getOriginalFilename(), image);
    }
}
