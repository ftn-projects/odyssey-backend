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
public class ReportCreationDTO {
    private UserDTO submitter;
    private String description;
    private LocalDateTime submissionDate;

    public ReportCreationDTO(Report report){
        submitter = new UserDTO(report.getSubmitter());
        description = report.getDescription();
        submissionDate = report.getSubmissionDate();
    }
}
