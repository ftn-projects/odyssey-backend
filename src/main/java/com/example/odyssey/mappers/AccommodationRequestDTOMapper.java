package com.example.odyssey.mappers;

import com.example.odyssey.dtos.accommodations.AccommodationRequestDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationRequestDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public AccommodationRequestDTOMapper(ModelMapper mapper){AccommodationRequestDTOMapper.mapper = mapper;}

    public static AccommodationRequest fromDTOtoAccommodationRequest(AccommodationRequestDTO dto) {return mapper.map(dto, AccommodationRequest.class);}

    public static AccommodationRequestDTO fromAccommodationRequestToDTO(AccommodationRequest request){
        return new AccommodationRequestDTO(request);
    }
}
