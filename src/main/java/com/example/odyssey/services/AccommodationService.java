package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccommodationService {
    public boolean slotsOverlap(Set<AvailabilitySlot> slots) {
        for(AvailabilitySlot i:slots)
            for(AvailabilitySlot j:slots)
                if(i!=j && i.getTimeSlot().isOverlap(j.getTimeSlot()))
                    return true;
        return false;
    }
}
