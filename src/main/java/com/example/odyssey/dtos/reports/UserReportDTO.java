package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.UserReport;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/*
    Used for admin User management feature.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDTO {
    private Long id;
    @Size(max = 150, message = "Description should be shorter than 150 characters.")
    private String description;
    private LocalDateTime submissionDate;
    @NotNull(message = "Submitter must be set.")
    private UserDTO submitter;
    @NotNull(message = "Reported user must be set.")
    private UserDTO reported;

    public UserReportDTO(UserReport report) {
        id = report.getId();
        description = report.getDescription();
        submissionDate = report.getSubmissionDate().truncatedTo(ChronoUnit.SECONDS);
        submitter = new UserDTO(report.getSubmitter());
        reported = new UserDTO(report.getReported());
    }
}
