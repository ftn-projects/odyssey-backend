package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.reviews.ReviewDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
    Used for admin Reviews management feature.
 */
@Getter
@Setter
@NoArgsConstructor
public class ReviewReportDTO extends ReportDTO {
    private ReviewDTO review;

    public ReviewReportDTO(Long id, String description, LocalDateTime submissionDate, UserDTO submitter, ReviewDTO review) {
        super(id, description, submissionDate, submitter);
        this.review = review;
    }

    public ReviewReportDTO(ReviewReport report) {
        super(report);
        review = new ReviewDTO(report.getReview());
    }
}
