package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
import com.example.odyssey.validation.AccommodationConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private Long id;
    @NotBlank(message = "Title should not be blank.")
    private String title;
    @NotBlank(message = "Request type must not be blank.")
    @Size(max = 800, message = "Description should be shorter than 800 characters.")
    private String description;
    private Accommodation.Type type;
    @NotNull(message = "Address must be set.")
    private AddressDTO address;
    @NotNull(message = "Pricing type must be set.")
    private Accommodation.PricingType pricing;
    private Set<AmenityDTO> amenities = new HashSet<>();
    @NotNull(message = "Host must be set.")
    private UserDTO host;
    private Double defaultPrice;
    @NotNull(message = "Automatic approval must be set.")
    private Boolean automaticApproval;
    @NotNull(message = "Cancellation due must be set.")
    @Positive(message = "Cancellation due must be a positive integer.")
    private Long cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    @Positive(message = "Min guests must be positive.")
    private Integer minGuests;
    @Positive(message = "Max guests must be positive.")
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

    public AccommodationDTO(AccommodationRequest.Details details) {
        title = details.getNewTitle();
        description = details.getNewDescription();
        type = details.getNewAccommodationType();
        address = new AddressDTO(details.getNewAddress());
        pricing = details.getNewPricing();
        details.getNewAmenities().forEach((a) -> amenities.add(new AmenityDTO(a)));
        defaultPrice = details.getNewDefaultPrice();
        automaticApproval = details.getNewAutomaticApproval();
        cancellationDue = details.getNewCancellationDue().toDays();
        details.getNewAvailableSlots().forEach((s) -> availableSlots.add(new AvailabilitySlotDTO(s)));
        minGuests = details.getNewMinGuests();
        maxGuests = details.getNewMaxGuests();
    }
}
