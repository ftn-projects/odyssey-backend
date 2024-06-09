package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.users.UserDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReportSubmissionDTO {
    @Size(max = 150, message = "Description should be shorter than 150 characters.")
    private String description;
    @NotNull(message = "Submitter must be set.")
    private Long submitterId;
    @NotNull(message = "Reported user must be set.")
    private Long reportedId;
}
