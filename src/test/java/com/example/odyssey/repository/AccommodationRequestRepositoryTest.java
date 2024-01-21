package com.example.odyssey.repository;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.repositories.AccommodationRequestRepository;
import com.example.odyssey.repositories.AmenityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AccommodationRequestRepositoryTest {
    @Autowired
    private AccommodationRequestRepository requestRepository;
    @Autowired
    private AmenityRepository amenityRepository;

    @Test
    public void shouldSaveRequest(){
        Set<AvailabilitySlot> slots = new HashSet<>();
        slots.add(new AvailabilitySlot(300D, new TimeSlot(LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(5))));

        Set<Amenity> amenities = new HashSet<>();
        amenities.add(new Amenity(null,"Coffee machine"));

        Set<String> images = new HashSet<>();
        images.add("tropical1.webp");

        Duration duration = Duration.between(LocalDateTime.now().plusDays(5), LocalDateTime.now());

        AccommodationRequest request = new AccommodationRequest(null, LocalDateTime.now(), AccommodationRequest.Type.CREATE, AccommodationRequest.Status.REQUESTED,
                new AccommodationRequest.Details("title","description", Accommodation.Type.HOUSE, new Address("street","city","country"),
                Accommodation.PricingType.PER_NIGHT, 300D, false, duration, slots, amenities, 1,3,images ),
                new Host(), null );

        AccommodationRequest savedRequest = requestRepository.save(request);
        assertThat(savedRequest).usingRecursiveComparison().ignoringFields("id").isEqualTo(request);
    }

    @Test
    @Sql("classpath:request-test.sql")
    public void shouldSaveRequestThroughSqlFile(){
        Optional<AccommodationRequest> test = requestRepository.findById(2L);
        assertThat(test).isNotEmpty();
    }

}
