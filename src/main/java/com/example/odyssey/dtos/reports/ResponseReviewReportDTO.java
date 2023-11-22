package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.reviews.ResponseReviewDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    Used for admin Reviews management feature.
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseReviewReportDTO extends ResponseReportDTO {
    private ResponseReviewDTO review;

    public ResponseReviewReportDTO(ReviewReport report) {
        super(report);
        review = new ResponseReviewDTO(report.getReportedReview());
    }
}
