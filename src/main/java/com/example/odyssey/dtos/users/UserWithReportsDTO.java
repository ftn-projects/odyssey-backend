package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserWithReportsDTO extends UserDTO {
    private List<UserReportDTO> reports;

    public UserWithReportsDTO(User user, List<UserReport> reports) {
        super(user);
        this.reports = reports.stream().map(UserReportDTO::new).toList();
    }
}
