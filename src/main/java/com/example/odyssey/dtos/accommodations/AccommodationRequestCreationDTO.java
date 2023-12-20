package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
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
public class AccommodationRequestCreationDTO {
    private Long id;
    private AccommodationRequest.Type requestType;
    private String newTitle;
    private String newDescription;
    private Accommodation.Type newType;
    private AddressDTO newAddress;
    private Accommodation.PricingType newPricing;
    private Double newDefaultPrice;
    private Boolean newAutomaticApproval;
    private Long newCancellationDue;
    private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
    private Set<Amenity> newAmenities = new HashSet<>();
    private Integer newMinGuests;
    private Integer newMaxGuests;
    private Set<String> newImages = new HashSet<>();
    private Long hostId;
    private Long accommodationId;
}
