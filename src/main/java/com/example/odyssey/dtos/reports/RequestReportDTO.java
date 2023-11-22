package com.example.odyssey.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class RequestReportDTO {
    private Long submitterId;
    private String description;
    private LocalDateTime submissionDate;
}
