package com.example.odyssey.dtos.accommodations;

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
    private String host;
    private Long accommodationId;

    public AccommodationRequestDTO(AccommodationRequest accommodationRequest) {
        id = accommodationRequest.getId();
        submissionDate = accommodationRequest.getSubmissionDate().toLocalDate();
        type = accommodationRequest.getType();
        title = accommodationRequest.getDetails().getNewTitle();
        host= accommodationRequest.getHost().getName() + " " +accommodationRequest.getHost().getSurname();
        accommodationId = accommodationRequest.getId();
    }
}
