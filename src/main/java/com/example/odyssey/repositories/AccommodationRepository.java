package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @NonNull
    Page<Accommodation> findAll(Pageable page);

    Accommodation findOneById(Long id);

    List<Accommodation> findAllByHost(Host host);

    List<Accommodation> findByTitleContaining(String title);

    List<Accommodation> findAllByAmenitiesContaining(Set<Amenity> amenities);

    List<Accommodation> findByMinGuestsLessThanEqualAndMaxGuestsGreaterThanEqual(int minGuests, int maxGuests);

    List<Accommodation> findByDefaultPriceBetween(Double priceStart, Double priceEnd);

    List<Accommodation> findAllByType(Accommodation.Type type);
}
