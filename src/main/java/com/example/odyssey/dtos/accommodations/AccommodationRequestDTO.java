package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.entity.accommodations.AccommodationRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestDTO {
    private Long id;
    private LocalDateTime submissionDate;
    private AccommodationRequest.Type type;
    private String title;
    private String hostName;
    private String hostSurname;
    private Long accommodationId;

    public AccommodationRequestDTO(AccommodationRequest accommodationRequest) {
        id = accommodationRequest.getId();
        submissionDate = accommodationRequest.getSubmissionDate();
        type = accommodationRequest.getType();
        title = accommodationRequest.getDetails().getNewTitle();
        hostName = accommodationRequest.getHost().getName();
        hostSurname = accommodationRequest.getHost().getSurname();
        accommodationId = accommodationRequest.getId();
    }
}
