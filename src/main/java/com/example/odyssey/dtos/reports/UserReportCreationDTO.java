package com.example.odyssey.dtos.reports;

import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportCreationDTO extends ReportCreationDTO {
    private User reported;

    public UserReportCreationDTO(UserReport review){
        super();
        reported = review.getReported();
    }
}
