package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.UserReport;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
    Used for admin User management feature.
 */
@Getter
@Setter
@NoArgsConstructor
public class ResponseUserReportDTO extends ResponseReportDTO {
    private UserDTO reportedUser;

    public ResponseUserReportDTO(UserReport report) {
        super(report);
        reportedUser = new UserDTO(report.getReportedUser());
    }
}
