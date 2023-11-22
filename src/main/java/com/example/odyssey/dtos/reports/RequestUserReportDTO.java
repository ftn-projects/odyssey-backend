package com.example.odyssey.dtos.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestUserReportDTO extends RequestReportDTO {
    private Long userId;

    public RequestUserReportDTO(Long submitterId, String description, LocalDateTime submissionDate, Long userId) {
        super(submitterId, description, submissionDate);
        this.userId = userId;
    }
}
