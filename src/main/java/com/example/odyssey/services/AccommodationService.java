package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Accommodation> getAll(
    ){
        return accommodationRepository.findAll();
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
}
