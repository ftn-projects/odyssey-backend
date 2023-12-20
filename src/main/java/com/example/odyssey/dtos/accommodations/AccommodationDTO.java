package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String title;
    private String description;
    private Accommodation.Type type;
    private AddressDTO address;
    private Accommodation.PricingType pricing;
    private Set<AmenityDTO> amenities = new HashSet<>();
    private UserDTO host;
    private Double defaultPrice;
    private Boolean automaticApproval;
    private Long cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    private Integer minGuests;
    private Integer maxGuests;
    private Double totalPrice;
    private Double averageRating;

    public AccommodationDTO(Accommodation accommodation) {
        id = accommodation.getId();
        title = accommodation.getTitle();
        description = accommodation.getDescription();
        type = accommodation.getType();
        address = new AddressDTO(accommodation.getAddress());
        pricing = accommodation.getPricing();
        accommodation.getAmenities().forEach((a) -> amenities.add(new AmenityDTO(a)));
        host = new UserDTO(accommodation.getHost());
        defaultPrice = accommodation.getDefaultPrice();
        automaticApproval = accommodation.getAutomaticApproval();
        cancellationDue = accommodation.getCancellationDue().toDays();
        accommodation.getAvailableSlots().forEach((s) -> availableSlots.add(new AvailabilitySlotDTO(s)));
        minGuests = accommodation.getMinGuests();
        maxGuests = accommodation.getMaxGuests();
    }
}
