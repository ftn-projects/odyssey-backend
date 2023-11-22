package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAccommodationDetailsDTO extends ResponseAccommodationSummaryDTO {
    private Duration cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    private Set<AmenityDTO> amenities = new HashSet<>();
    private Integer minGuests;
    private Integer maxGuests;

    public ResponseAccommodationDetailsDTO(Accommodation accommodation, Double calculatedPrice) {
        super(accommodation, calculatedPrice);
        cancellationDue = accommodation.getCancellationDue();
        accommodation.getAvailableSlots().forEach((s) -> availableSlots.add(new AvailabilitySlotDTO(s)));
        accommodation.getAmenities().forEach((a) -> amenities.add(new AmenityDTO(a)));
        minGuests = accommodation.getMinGuests();
        maxGuests = accommodation.getMaxGuests();
    }
}
