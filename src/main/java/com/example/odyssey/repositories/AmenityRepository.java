package com.example.odyssey.repositories;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Amenity findAmenityById(Long id);
}
