package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestDTO {
    private Long id;
    private LocalDate submissionDate;
    private AccommodationRequest.Type type;
    private String title;
    private UserDTO host;
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
