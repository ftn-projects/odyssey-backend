package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reports.UserReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportDTO extends ReportDTO{
    private UserDTO reported;

    public UserReportDTO(UserReport report){
        reported = new UserDTO(report.getReported());
    }
}
