package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportSubmissionDTO {
    private String description;
    private Long submitterId;
    private Long reportedId;
}
