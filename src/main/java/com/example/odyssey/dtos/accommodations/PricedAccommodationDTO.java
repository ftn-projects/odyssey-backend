package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PricedAccommodationDTO extends AccommodationDTO {
    private double totalPrice;

    public PricedAccommodationDTO(Long id, String title, String description, Accommodation.Type type, AddressDTO address, Accommodation.PricingType pricing, Double defaultPrice, Boolean automaticApproval, Duration cancellationDue, Set<AvailabilitySlotDTO> availableSlots, Set<AmenityDTO> amenities, Integer minGuests, Integer maxGuests, UserDTO host, double totalPrice) {
        super(id, title, description, type, address, pricing, defaultPrice, automaticApproval, cancellationDue, availableSlots, amenities, minGuests, maxGuests, host);
        this.totalPrice = totalPrice;
    }

    public PricedAccommodationDTO(Accommodation accommodation, double totalPrice) {
        super(accommodation);
        this.totalPrice = totalPrice;
    }
}
