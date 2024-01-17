package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.exceptions.InputValidationException;
import com.example.odyssey.exceptions.InvalidAvailabilitySlotException;
import com.example.odyssey.exceptions.SlotHasReservationsException;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import com.example.odyssey.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccommodationRequestService {
    @Autowired
    private AccommodationRequestRepository repository;
    @Autowired
    private AccommodationService accommodationService;
    @Autowired
    private ReservationService reservationService;

    private final String imagesDirPath = "src/main/resources/images/accommodationRequests/";

    public List<AccommodationRequest> findByStatus(AccommodationRequest.Status status) {
        return repository.findAccommodationRequestByStatus(status);
    }

    public AccommodationRequest findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Accommodation request with id '%d' does not exist.", id)));
    }

    public void editStatus(AccommodationRequest request, AccommodationRequest.Status status) throws IOException {
        if (status.equals(AccommodationRequest.Status.ACCEPTED))
            acceptRequest(request);

        request.setStatus(status);
        repository.save(request);
    }

    public void acceptRequest(AccommodationRequest request) throws IOException {
        Accommodation accommodation;

        if (request.getType().equals(AccommodationRequest.Type.CREATE)) {
            accommodation = new Accommodation(request.getDetails());
            accommodation.setHost(request.getHost());
        } else {
            accommodation = accommodationService.getOne(request.getAccommodationId());
            accommodation.updateWithDetails(request.getDetails());
        }

        Long id = accommodationService.save(accommodation).getId();
        ImageUtil.copyFiles(
                imagesDirPath + request.getId(),
                AccommodationService.imagesDirPath + id);
    }

    public AccommodationRequest create(AccommodationRequest.Type type, AccommodationRequest.Details details, Host host, Long accommodationId) {
        AccommodationRequest request = new AccommodationRequest(
                -1L, LocalDateTime.now(), type, AccommodationRequest.Status.REQUESTED, details, host, accommodationId);

         for(AvailabilitySlot slot: request.getDetails().getNewAvailableSlots()){
             if(slot.getPrice()<=0 || slot.getTimeSlot().getEnd().isBefore(LocalDateTime.now())
                     || slot.getPrice() == null) throw new InvalidAvailabilitySlotException("newAvailableSlots");


             if(request.getType().equals(AccommodationRequest.Type.UPDATE) &&
                     reservationService.overlapsReservation(request.getAccommodationId(), slot.getTimeSlot())) throw new SlotHasReservationsException("newAvailableSlots");
         }

        return repository.save(request);
    }

    public byte[] getImage(Long id, String imageName) throws IOException {
        String accommodationDirPath = imagesDirPath + id;

        Path imageFilePath = Paths.get(accommodationDirPath, imageName);

        if (!Files.exists(imageFilePath) || Files.isDirectory(imageFilePath)) {
            throw new IOException("Image not found: " + imageName);
        }

        return Files.readAllBytes(imageFilePath);
    }

    public List<String> getImageNames(Long id) throws IOException {
        String accommodationDirPath = imagesDirPath + id;

        Path accommodationDir = Paths.get(accommodationDirPath);

        if (!Files.exists(accommodationDir) || !Files.isDirectory(accommodationDir)) {
            throw new IOException("Accommodation request directory not found for id: " + id);
        }

        List<String> imageNames = new ArrayList<>();

        try (var stream = Files.walk(accommodationDir)) {
            stream.filter(Files::isRegularFile)
                    .forEach(filePath -> {
                        imageNames.add(filePath.getFileName().toString());
                    });
        }

        return imageNames;
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        if (image.getOriginalFilename() == null)
            throw new IOException("Image is non existing.");

        findById(id); // id validation

        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);
        ImageUtil.saveImage(uploadDir, image.getOriginalFilename(), image);
    }
}
