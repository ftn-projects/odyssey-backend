package com.example.odyssey.services;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.repositories.AccommodationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;


    public List<Accommodation> getAll(
            Long dateStart,
            Long dateEnd,
            Integer guestNumber,
            List<Amenity> amenities,
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





    public void createDummyData() {
        // Create a sample host
        Host host = new Host();
        host.setRole(User.Role.HOST);
        host.setStatus(User.AccountStatus.ACTIVE);
        host.setName("Sample Host");
        host.setEmail("host@example.com");
        host.setPassword("host123");
        host.setPhone("+123456789");

        // Create a sample accommodation
        Accommodation accommodation = new Accommodation();
        accommodation.setTitle("Cozy Cabin");
        accommodation.setDescription("A comfortable cabin in the woods.");
        accommodation.setType(Accommodation.Type.HOUSE);
        accommodation.setPricing(Accommodation.PricingType.PER_PERSON);
        accommodation.setHost(host);
        accommodation.setDefaultPrice(150.0);
        accommodation.setAutomaticApproval(true);
        accommodation.setMinGuests(2);
        accommodation.setMaxGuests(4);

        // Create sample amenities
        Amenity amenity1 = new Amenity();
        amenity1.setTitle("WiFi");
        Amenity amenity2 = new Amenity();
        amenity2.setTitle("Parking");

        accommodation.setAmenities(new HashSet<>(Arrays.asList(amenity1, amenity2)));

        // Create sample availability slots
        AvailabilitySlot slot1 = new AvailabilitySlot();
        slot1.setPrice(150.0);

        // Create sample time slots
        TimeSlot timeSlot1 = new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(7));
        slot1.setTimeSlot(timeSlot1);

        AvailabilitySlot slot2 = new AvailabilitySlot();
        slot2.setPrice(180.0);

        // Create another sample time slot
        TimeSlot timeSlot2 = new TimeSlot(LocalDateTime.now().plusDays(15), LocalDateTime.now().plusDays(30));
        slot2.setTimeSlot(timeSlot2);

        accommodation.setAvailableSlots(new HashSet<>(Arrays.asList(slot1, slot2)));

        // Create sample address
        Address address = new Address();
        address.setCity("Woodland");
        address.setCountry("Natureland");
        address.setStreet("123 Forest Lane");
        address.setNumber(23);

        accommodation.setAddress(address);

        // Save the accommodation with dummy data
        accommodationRepository.save(accommodation);
    }
}
