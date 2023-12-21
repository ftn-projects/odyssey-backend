package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotNull
    private AccommodationRequest.Type requestType;
    @NotBlank(message = "Title should not be blank.")
    private String newTitle;
    @NotBlank
    @Size(max = 800, message = "Description should be shorter than 800 characters.")
    private String newDescription;
    @NotNull
    private Accommodation.Type newType;
    @NotNull
    private AddressDTO newAddress;
    @NotNull
    private Accommodation.PricingType newPricing;
    @Positive
    private Double newDefaultPrice;
    @NotNull
    private Boolean newAutomaticApproval;
    @NotNull
    private Long newCancellationDue;
    @NotNull
    private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
    @NotNull
    private Set<Amenity> newAmenities = new HashSet<>();
    @Positive
    private Integer newMinGuests;
    @Positive
    private Integer newMaxGuests;
    @NotNull
    private Set<String> newImages = new HashSet<>();
    @NotNull
    private Long hostId;
    private Long accommodationId;
}
