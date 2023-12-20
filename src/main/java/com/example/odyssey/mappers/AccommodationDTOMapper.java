package com.example.odyssey.mappers;

import com.example.odyssey.dtos.accommodations.AccommodationRequestCreationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.accommodations.AccommodationDetailsDTO;
import com.example.odyssey.dtos.accommodations.AccommodationSearchDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationDTOMapper {

    private static ModelMapper mapper;

    @Autowired
    public AccommodationDTOMapper(ModelMapper mapper) {
        AccommodationDTOMapper.mapper = mapper;
    }

    public static AccommodationDTO fromAccommodationToDTO(Accommodation accommodation) {
        return new AccommodationDTO(accommodation);
    }

    public static Accommodation fromDTOToAccommodation(AccommodationDTO dto) {
        return mapper.map(dto, Accommodation.class);
    }

    public static AccommodationDetailsDTO fromAccommodationToDetailsDTO(Accommodation accommodation) {
        return new AccommodationDetailsDTO(accommodation);
    }

    public static Accommodation fromDetailsDTOToAccommodation(AccommodationDetailsDTO dto) {
        return mapper.map(dto, Accommodation.class);
    }

    public static AccommodationSearchDTO fromAccommodationToSearchDTO(Accommodation accommodation, Double totalPrice, Double averageRating) {
        return new AccommodationSearchDTO(accommodation, totalPrice, averageRating);
    }

    public static Accommodation fromSearchDTOToAccommodation(AccommodationSearchDTO dto) {
        return mapper.map(dto, Accommodation.class);
    }

    public static AccommodationRequest.Details fromCreationDTOToAccommodationDetails(AccommodationRequestCreationDTO dto) {
        return mapper.map(dto, AccommodationRequest.Details.class);
    }
}
