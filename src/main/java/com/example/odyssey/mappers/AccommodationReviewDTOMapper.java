package com.example.odyssey.mappers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.reports.UserReport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccommodationReviewDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public AccommodationReviewDTOMapper(ModelMapper mapper) {
        AccommodationReviewDTOMapper.mapper = mapper;
    }

    public static Accommodation fromDTOtoUserReport(AccommodationDTO dto) {
        return mapper.map(dto, Accommodation.class);
    }

    public static AccommodationDTO fromUserReporToDTO(Accommodation model) {
        return mapper.map(model, AccommodationDTO.class);
    }
}
