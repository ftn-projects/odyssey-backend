package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationModificationRequest;
import com.example.odyssey.entity.accommodations.Amenity;
import com.example.odyssey.entity.accommodations.AvailabilitySlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationModificationRequestDTO {
    private LocalDateTime submissionDate;
    private AccommodationModificationRequest.Type type;
    private String newTitle;
    private String newDescription;
    private Accommodation.Type newAccommodationType;
    private AddressDTO address;
    private Double newDefaultPrice;
    private Boolean newAutomaticApproval;
    private Duration newCancellationDue;
    private Set<AvailabilitySlot> newAvailableSlots;
    private Set<Amenity> newAmenities = new HashSet<>();
    private Integer newMinGuests;
    private Integer newMaxGuests;
    private Accommodation accommodation;

    public AccommodationModificationRequestDTO(AccommodationModificationRequest request){
        submissionDate = request.getSubmissionDate();
        type = request.getType();
        newTitle = request.getNewTitle();
        newDescription = request.getNewDescription();
        newAccommodationType = request.getNewAccommodationType();
        address = new AddressDTO(request.getNewAddress());
        newDefaultPrice = request.getNewDefaultPrice();
        newAutomaticApproval = request.getNewAutomaticApproval();
        newCancellationDue = request.getNewCancellationDue();
        newAvailableSlots = request.getNewAvailableSlots();
        newAmenities = request.getNewAmenities();
        newMinGuests = request.getNewMinGuests();
        newMaxGuests = request.getNewMaxGuests();
        accommodation = request.getAccommodationId();
    }

}
