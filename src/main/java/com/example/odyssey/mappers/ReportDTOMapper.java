package com.example.odyssey.mappers;

import com.example.odyssey.dtos.reports.ReportDTO;
import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.dtos.reviews.AccommodationReviewDTO;
import com.example.odyssey.dtos.reviews.HostReviewDTO;
import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.reports.Report;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reviews.AccommodationReview;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public ReportDTOMapper(ModelMapper mapper) {
        ReportDTOMapper.mapper = mapper;
    }

    public static ReviewReport fromDTOtoReviewReport(ReviewReportDTO dto) {
        return mapper.map(dto, ReviewReport.class);
    }

    public static ReviewReportDTO fromReviewReportToDTO(ReviewReport report) {
        return new ReviewReportDTO(report);
    }

    public static UserReport fromDTOtoUserReport(UserReportDTO dto) {
        return mapper.map(dto, UserReport.class);
    }

    public static UserReportDTO fromUserReportToDTO(UserReport report) {
        return new UserReportDTO(report);
    }

    public static ReportDTO fromReportToDTO(Report report) {
        if (report instanceof ReviewReport)
            return new ReviewReportDTO((ReviewReport) report);
        return new UserReportDTO((UserReport) report);
    }
}
