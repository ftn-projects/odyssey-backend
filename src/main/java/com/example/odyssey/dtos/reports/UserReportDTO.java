package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.UserReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/*
    Used for admin User management feature.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserReportDTO extends ReportDTO {
    private UserDTO reportedUser;

    public UserReportDTO(Long id, String description, LocalDateTime submissionDate, UserDTO submitter, UserDTO reportedUser) {
        super(id, description, submissionDate, submitter);
        this.reportedUser = reportedUser;
    }

    public UserReportDTO(UserReport report) {
        super(report);
        reportedUser = new UserDTO(report.getReportedUser());
    }
}
