package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.repositories.AccommodationRepository;
import com.example.odyssey.repositories.AmenityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AmenityRepository amenityRepository;

    public static final String imagesDirPath = "src/main/resources/images/accommodations/";

    public List<Accommodation> getAll(
            String location,
            Long dateStart,
            Long dateEnd,
            Integer guestNumber,
            List<Long> amenities,
            String type,
            Double priceStart,
            Double priceEnd
    ) {

        LocalDateTime startDate = (dateStart != null) ? new ReservationService().convertToDate(dateStart) : null;
        LocalDateTime endDate = (dateEnd != null) ? new ReservationService().convertToDate(dateEnd) : null;
        Accommodation.Type accommodationType = (type != null) ? Accommodation.Type.valueOf(type) : null;
        return accommodationRepository.findAllWithFilter(
                guestNumber, accommodationType, amenities, startDate, endDate, priceStart, priceEnd, location
        );
    }

    public List<Accommodation> getAllMM() {
        return accommodationRepository.findAll();
    }

    public Accommodation getOne(Long id) {
        return accommodationRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("Accommodation with the id '%s' does not exist.", id)));
    }

    public List<Accommodation> findByHost(Host host) {
        return accommodationRepository.findAllByHost(host);
    }

    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    public boolean slotsOverlap(Set<AvailabilitySlot> slots) {
        for (AvailabilitySlot i : slots)
            for (AvailabilitySlot j : slots)
                if (i != j && i.getTimeSlot().overlaps(j.getTimeSlot()))
                    return true;
        return false;
    }

    public byte[] getImage(Long id, String imageName) throws IOException {
        getOne(id); // id validation

        String accommodationDirPath = imagesDirPath + id;

        Path imageFilePath = Paths.get(accommodationDirPath, imageName);

        if (!Files.exists(imageFilePath) || Files.isDirectory(imageFilePath)) {
            throw new IOException("Image not found: " + imageName);
        }

        return Files.readAllBytes(imageFilePath);
    }

    public List<String> getImageNames(Long id) throws IOException {
        getOne(id); // id validation

        String accommodationDirPath = imagesDirPath + id;

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

        return imageNames.stream().sorted().toList();
    }

    public List<Amenity> getAmenities() {
        return amenityRepository.findAll();
    }

    public Double getPriceForDateRange(Long accommodationID, Long startDateLong, Long endDateLong) {
        if (accommodationID == null || startDateLong == null || endDateLong == null)
            return (double) -1;
        LocalDateTime startDate = new ReservationService().convertToDate(startDateLong);
        LocalDateTime endDate = new ReservationService().convertToDate(endDateLong);
        return accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate);
    }

    public Double calculateTotalPrice(Long accommodationID, Long startDateLong, Long endDateLong, Integer guestNumber) {
        if (accommodationID == null || startDateLong == null || endDateLong == null)
            return (double) -1;
        LocalDateTime startDate = new ReservationService().convertToDate(startDateLong);
        LocalDateTime endDate = new ReservationService().convertToDate(endDateLong);
        Accommodation accommodation = getOne(accommodationID);

        long days = endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay() + 1;
        Double priceForRange = accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate);
        if (priceForRange == null) {
            return (double) -1;
        }
        if (accommodation.getPricing() == Accommodation.PricingType.PER_NIGHT) {
            System.out.println("Per night price for range: " + priceForRange);
            return (days * accommodationRepository.findPriceForDateRange(accommodationID, startDate, endDate));
        } else if (accommodation.getPricing() == Accommodation.PricingType.PER_PERSON)
            if (guestNumber != null && guestNumber > 0) {
                return (days * priceForRange * guestNumber);
            } else
                return (double) -1;
        else
            return (double) -1;
    }
}
