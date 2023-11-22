package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAccommodationSummaryDTO extends ResponseAccommodationBaseDTO {
    private Double calculatedPrice;

    public ResponseAccommodationSummaryDTO(Accommodation accommodation, Double calculatedPrice) {
        super(accommodation);
        this.calculatedPrice = calculatedPrice;
    }
}
