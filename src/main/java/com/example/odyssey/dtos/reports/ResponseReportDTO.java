package com.example.odyssey.dtos.reports;

import com.example.odyssey.entity.reports.Report;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.odyssey.dtos.users.ResponseUserDTO;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public abstract class ResponseReportDTO {
    private Long id;
    private String description;
    private LocalDateTime submissionDate;
    private ResponseUserDTO submitter;

    public ResponseReportDTO(Report report) {
        id = report.getId();
        description = report.getDescription();
        submissionDate = report.getSubmissionDate();
        submitter = new ResponseUserDTO(report.getSubmitter());
    }
}
