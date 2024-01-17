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

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestCreationDTO {
    private Long id;
    @NotNull(message = "Request type must be set.")
    private AccommodationRequest.Type requestType;
    @NotBlank(message = "Title should not be blank.")
    private String newTitle;
    @NotBlank(message = "Description should not be blank.")
    @Size(max = 800, message = "Description should be shorter than 800 characters.")
    private String newDescription;
    @NotNull(message = "Accommodation type must be set.")
    private Accommodation.Type newType;
    @NotNull(message = "Address must be set.")
    private AddressDTO newAddress;
    @NotNull(message = "Pricing type must be set.")
    private Accommodation.PricingType newPricing;
    @NotNull(message = "Default price must be set.")
    @Positive(message = "Default price must be positive.")
    private Double newDefaultPrice;
    @NotNull(message = "Automatic approval must be set.")
    private Boolean newAutomaticApproval;
    @NotNull(message = "Cancellation due must be set.")
    @Positive(message = "Cancellation due must be positive.")
    private Long newCancellationDue;
    @NotNull(message = "Available slots must be set.")
    private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
    @NotNull(message = "Amenities must be set.")
    private Set<Amenity> newAmenities = new HashSet<>();
    @Positive(message = "Minimum guests number must be positive.")
    private Integer newMinGuests;
    @Positive(message = "Maximum guests number must be positive.")
    private Integer newMaxGuests;
    @NotNull(message = "Images must be set.")
    private Set<String> newImages = new HashSet<>();
    @NotNull(message = "Host id must be set.")
    private Long hostId;
    private Long accommodationId;
}
