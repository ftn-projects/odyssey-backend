package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReviewReportDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReviewReportDTOMapper(ModelMapper mapper) {
        ReviewReportDTOMapper.mapper = mapper;
    }

    public static ReviewReport fromDTOtoReviewReport(ReviewReportDTO dto) {
        return mapper.map(dto, ReviewReport.class);
    }

    public static ReviewReportDTO fromReviewReportToDTO(ReviewReport model) {
        return mapper.map(model, ReviewReportDTO.class);
    }
}
