package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReportDTO extends ReportDTO {
    private ReviewDTO review;

    public ReviewReportDTO(ReviewReport report){
        super();
        review = new ReviewDTO(report.getReported());
    }
}
