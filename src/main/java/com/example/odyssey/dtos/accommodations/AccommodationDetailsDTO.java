package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
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
public class AccommodationDetailsDTO extends AccommodationDTO {
    private Double defaultPrice;
    private Boolean automaticApproval;
    private Duration cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    private Integer minGuests;
    private Integer maxGuests;

    public AccommodationDetailsDTO(Long id, String title, String description, Accommodation.Type type, AddressDTO address, Accommodation.PricingType pricing, Set<AmenityDTO> amenities, UserDTO host, Double defaultPrice, Boolean automaticApproval, Duration cancellationDue, Set<AvailabilitySlotDTO> availableSlots, Integer minGuests, Integer maxGuests) {
        super(id, title, description, type, address, pricing, amenities, host);
        this.defaultPrice = defaultPrice;
        this.automaticApproval = automaticApproval;
        this.cancellationDue = cancellationDue;
        this.availableSlots = availableSlots;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }

    public AccommodationDetailsDTO(Accommodation accommodation) {
        super(accommodation);
        defaultPrice = accommodation.getDefaultPrice();
        automaticApproval = accommodation.getAutomaticApproval();
        cancellationDue = accommodation.getCancellationDue();
        accommodation.getAvailableSlots().forEach((s) -> availableSlots.add(new AvailabilitySlotDTO(s)));
        minGuests = accommodation.getMinGuests();
        maxGuests = accommodation.getMaxGuests();
    }
}
