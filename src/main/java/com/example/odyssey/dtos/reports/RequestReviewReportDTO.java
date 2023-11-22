package com.example.odyssey.dtos.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestReviewReportDTO extends RequestReportDTO {
    private Long reviewId;

    public RequestReviewReportDTO(Long submitterId, String description, LocalDateTime submissionDate, Long reviewId) {
        super(submitterId, description, submissionDate);
        this.reviewId = reviewId;
    }
}
