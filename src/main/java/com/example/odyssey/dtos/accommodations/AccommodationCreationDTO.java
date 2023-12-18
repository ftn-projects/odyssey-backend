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
public class AccommodationCreationDTO {
    private Long id;
    private String title;
    private String description;
    private Accommodation.Type type;
    private AddressDTO address;
    private Accommodation.PricingType pricing;
    private Set<AmenityDTO> amenities = new HashSet<>();
    private Long hostId;
    private Double defaultPrice;
    private Boolean automaticApproval;
    private Duration cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    private Integer minGuests;
    private Integer maxGuests;
}