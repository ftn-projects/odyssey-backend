package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.users.Host;
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

@Service
public class AccommodationRequestService {
    @Autowired
    private AccommodationRequestRepository repository;
    @Autowired
    private AccommodationService accommodationService;

    private final String imagesDirPath = "src/main/resources/images/accommodationRequests/";

    public List<AccommodationRequest> findByStatus(AccommodationRequest.Status status) {
        return repository.findAccommodationRequestByStatus(status);
    }

    public AccommodationRequest findById(Long id) {
        return repository.findAccommodationRequestById(id);
    }

    public void editStatus(AccommodationRequest request, AccommodationRequest.Status status) throws IOException {
        Accommodation accommodation;
        if (status.equals(AccommodationRequest.Status.ACCEPTED)) {
            Long id;
            if (request.getType().equals(AccommodationRequest.Type.CREATE)) {
                accommodation = new Accommodation(request.getDetails());
                accommodation.setHost(request.getHost());
                id = accommodationService.save(accommodation).getId();
            } else {
                accommodationService.editAccommodation(request.getAccommodationId(), request.getDetails());
                id = request.getAccommodationId();
            }

            ImageUtil.copyFiles(
                    imagesDirPath + request.getId(),
                    AccommodationService.imagesDirPath + id);
        }
        request.setStatus(status);
        repository.save(request);
    }

    public AccommodationRequest create(AccommodationRequest.Type type, AccommodationRequest.Details details, Host host, Long accommodationId) {
        AccommodationRequest request = new AccommodationRequest(
                -1L, LocalDateTime.now(), type, AccommodationRequest.Status.REQUESTED, details, host, accommodationId);
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
        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);
        ImageUtil.saveImage(uploadDir, image.getOriginalFilename(), image);
    }
}
