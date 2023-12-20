package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
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
import java.util.*;
import java.util.stream.Collectors;

import com.example.odyssey.entity.accommodations.AvailabilitySlot;
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
                if(i!=j && i.getTimeSlot().overlaps(j.getTimeSlot()))
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

    public double calculateTotalPrice(Long accommodationID, Long startDateLong, Long endDateLong, Integer guestNumber){
        if(accommodationID == null || startDateLong == null || endDateLong == null)
            return -1;
        LocalDateTime startDate = new ReservationService().convertToDate(startDateLong);
        LocalDateTime endDate = new ReservationService().convertToDate(endDateLong);
        Accommodation accommodation = getOne(accommodationID);

        long days = endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay();
        if(accommodation.getPricing()==Accommodation.PricingType.PER_ACCOMMODATION)
            return (days *  accommodation.getDefaultPrice());
        else if(accommodation.getPricing()==Accommodation.PricingType.PER_PERSON)
            if(guestNumber != null && guestNumber > 0)
                return (days *  accommodation.getDefaultPrice() * guestNumber);
            else
                return -1;
        else
            return -1;
    }

    public void editAccommodation(Long id, AccommodationRequest.ModificationDetails details){
        Accommodation accommodation = getOne(id);
        accommodation.setTitle(details.getNewTitle());
        accommodation.setDescription(details.getNewDescription());
        accommodation.setType(details.getNewAccommodationType());
        accommodation.setAddress(details.getNewAddress());
        accommodation.setDefaultPrice(details.getNewDefaultPrice());
        accommodation.setAutomaticApproval(details.getNewAutomaticApproval());
        accommodation.setCancellationDue(details.getNewCancellationDue());
        accommodation.setAvailableSlots(details.getNewAvailableSlots());
        accommodation.setAmenities(details.getNewAmenities());
        accommodation.setMinGuests(details.getNewMinGuests());
        accommodation.setMaxGuests(details.getNewMaxGuests());
        save(accommodation);
    }

    public Accommodation create(Accommodation accommodation, Collection<Long> amenityIds) {
        accommodation.setAmenities(new HashSet<>(amenityIds.stream().map(
                (a) -> amenityRepository.findAmenityById(a)).toList()));
        return accommodationRepository.save(accommodation);
    }
}
