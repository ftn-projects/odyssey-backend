package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reviews.HostReview;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HostReviewDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public HostReviewDTOMapper(ModelMapper mapper) {
        HostReviewDTOMapper.mapper = mapper;
    }

    public static HostReview fromDTOtoHostReview(HostReviewDTO dto) {
        return mapper.map(dto, HostReview.class);
    }

    public static HostReviewDTO fromHostReviewToDTO(HostReview model) {
        return mapper.map(model, HostReviewDTO.class);
    }
}
