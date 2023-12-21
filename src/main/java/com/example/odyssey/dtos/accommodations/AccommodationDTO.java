package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationRequest;
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
    @NotBlank
    @Size(max = 800, message = "Description should be shorter than 800 characters.")
    private String description;
    private Accommodation.Type type;
    @NotNull
    private AddressDTO address;
    @NotNull
    private Accommodation.PricingType pricing;
    private Set<AmenityDTO> amenities = new HashSet<>();
    @NotNull
    private UserDTO host;
    private Double defaultPrice;
    @NotNull
    private Boolean automaticApproval;
    @NotNull
    @Positive
    private Long cancellationDue;
    private Set<AvailabilitySlotDTO> availableSlots = new HashSet<>();
    @Positive
    private Integer minGuests;
    @Positive
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
