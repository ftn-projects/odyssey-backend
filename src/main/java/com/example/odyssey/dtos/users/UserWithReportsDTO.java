package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.Role;
import com.example.odyssey.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserWithReportsDTO extends UserDTO {
    private User.AccountStatus status;
    private String role;
    private List<UserReportDTO> reports;

    public UserWithReportsDTO(User user, List<UserReport> reports) {
        super(user);
        this.status = user.getStatus();
        this.role = user.getRoles().get(0).getName();
        this.reports = reports.stream().map(UserReportDTO::new).toList();
        if (user instanceof Host)
            this.setBio(((Host) user).getBio());
    }
}
