package com.example.odyssey.services;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.repositories.AccommodationRepository;
import com.example.odyssey.repositories.AmenityRepository;
import com.example.odyssey.util.ImageUploadUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    private static final String imagesDirPath = "src/main/resources/images/accommodations/";
    public List<Accommodation> getAll(
            Long dateStart,
            Long dateEnd,
            Integer guestNumber,
            List<Long> amenities,
            String type,
            Double priceStart,
            Double priceEnd
    ){

        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        Accommodation.Type accommodationType = (type != null) ? Accommodation.Type.valueOf(type) : null;
        return accommodationRepository.findAllWithFilter(
                guestNumber, accommodationType, amenities, startDate, endDate, priceStart, priceEnd
        );
    }

    public Accommodation getOne(Long id){
        return accommodationRepository.findOneById(id);
    }

    public List<Accommodation> findByHost(Host host){
        return accommodationRepository.findAllByHost(host);
    }

    public Accommodation save(Accommodation accommodation){return accommodationRepository.save(accommodation);}
    public boolean slotsOverlap(Set<AvailabilitySlot> slots) {
        for(AvailabilitySlot i:slots)
            for(AvailabilitySlot j:slots)
                if(i!=j && i.getTimeSlot().isOverlap(j.getTimeSlot()))
                    return true;
        return false;
    }

    public void uploadProfileImage(Long id, MultipartFile image) throws IOException {

    }

    public byte[] getImage(Long id, String imageName) throws IOException {
        String accommodationDirPath = imagesDirPath + "accommodation" + id;

        Path imageFilePath = Paths.get(accommodationDirPath, imageName);

        if (!Files.exists(imageFilePath) || Files.isDirectory(imageFilePath)) {
            throw new IOException("Image not found: " + imageName);
        }

        return Files.readAllBytes(imageFilePath);
    }

    public List<String> getImageNames(Long id) throws IOException {
        String accommodationDirPath = imagesDirPath + "accommodation" + id;

        Path accommodationDir = Paths.get(accommodationDirPath);

        if (!Files.exists(accommodationDir) || !Files.isDirectory(accommodationDir)) {
            throw new IOException("Accommodation directory not found for id: " + id);
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

    public List<Amenity> getAmenities() {
        return amenityRepository.findAll();
    }
}
