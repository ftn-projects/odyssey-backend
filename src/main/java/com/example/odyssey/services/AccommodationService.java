package com.example.odyssey.services;

import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.repositories.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public List<Accommodation> getAll(
    ){
        return accommodationRepository.findAll();
    }

    public List<Accommodation> filter(
            List<Accommodation> accommodations,
            String search,
            Long dateStart,
            Long dateEnd,
            Integer guestNumber,
            List<Long> amenities,
            String type,
            Double priceStart,
            Double priceEnd
    ){
        if(search!=null){
            accommodations.retainAll(findByTitle(search));
        }
//        if(dateStart!=null && dateEnd!=null){
//            accommodations.retainAll(findByAvailableSlots(dateStart, dateEnd));
//        }

        if(guestNumber!=null){
            accommodations.retainAll(findByGuestCount(guestNumber));
        }
//        if(amenities!=null){
//            accommodations.retainAll(findByAmenities((Set.copyOf(amenities))));
//        }
        if(type!=null){
            accommodations.retainAll(findByType(Accommodation.Type.valueOf(type)));
        }
        if(priceStart!=null && priceEnd!=null){
            accommodations.retainAll(findByPriceRange(priceStart, priceEnd));
        }
        return accommodations;
    }

    public Accommodation getOne(Long id){
        return accommodationRepository.findOneById(id);
    }

    public List<Accommodation> findByAmenities(Set<Amenity> amenities){
        return accommodationRepository.findAllByAmenitiesContaining(amenities);
    }
    public List<Accommodation> findByGuestCount(int guestCount) {
        return accommodationRepository.findByMinGuestsLessThanEqualAndMaxGuestsGreaterThanEqual(guestCount, guestCount);
    }

    public List<Accommodation> findByTitle(String title) {
        return accommodationRepository.findByTitleContaining(title);
    }

    public List<Accommodation> findByPriceRange(double priceStart, double priceEnd) {
        return accommodationRepository.findByDefaultPriceBetween(priceStart, priceEnd);
    }

    public List<Accommodation> findByType(Accommodation.Type type) {
        return accommodationRepository.findAllByType(type);
    }

    public Accommodation save(Accommodation accommodation){return accommodationRepository.save(accommodation);}
}
