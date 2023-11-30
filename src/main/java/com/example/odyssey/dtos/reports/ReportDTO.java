package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ReportDTO {
    private Long id;
    private String description;
    private LocalDateTime submissionDate;
    private UserDTO submitter;

    public ReportDTO(Report report) {
        id = report.getId();
        description = report.getDescription();
        submissionDate = report.getSubmissionDate();
        submitter = new UserDTO(report.getSubmitter());
    }
}
