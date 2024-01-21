package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestDTO {
    private Long id;
    @NotNull
    private LocalDate submissionDate;
    @NotNull
    private AccommodationRequest.Type type;
    @NotBlank(message = "Title should not be blank.")
    private String title;
    @NotNull
    private UserDTO host;
    @NotNull
    private AccommodationDTO details;
    private Long accommodationId;

    public AccommodationRequestDTO(AccommodationRequest accommodationRequest) {
        id = accommodationRequest.getId();
        submissionDate = accommodationRequest.getSubmissionDate().toLocalDate();
        type = accommodationRequest.getType();
        title = accommodationRequest.getDetails().getNewTitle();
        host = new UserDTO(accommodationRequest.getHost());
        details = new AccommodationDTO(accommodationRequest.getDetails());
        accommodationId = accommodationRequest.getId();
    }
}
