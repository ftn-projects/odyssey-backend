package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.users.HostDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;

import java.time.Duration;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    Long id;
    String title;
    String description;
    Accommodation.Type type;
    AddressDTO address;
    Accommodation.PricingType pricing;
    Double defaultPrice;
    Duration cancellationDue;
    Set<AvailabilitySlot> availableSlots;
    HostDTO host;

    public AccommodationDTO(Accommodation accommodation){
        id = accommodation.getId();
        title = accommodation.getTitle();
        description = accommodation.getDescription();
        type = accommodation.getType();
        address = new AddressDTO(accommodation.getAddress());
        pricing = accommodation.getPricing();
        defaultPrice = accommodation.getDefaultPrice();
        cancellationDue = accommodation.getCancellationDue();
        availableSlots = accommodation.getAvailableSlots();
        host = new HostDTO(accommodation.getHost());
    }
}
