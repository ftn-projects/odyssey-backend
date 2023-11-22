package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.accommodations.AccommodationModification;
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
public class RequestAccommodationModificationDTO {
    private LocalDateTime submissionDate;
    private AccommodationModification.Type type;
    private String newTitle;
    private String newDescription;
    private Accommodation.Type newAccommodationType;
    private AddressDTO address;
    private Double newDefaultPrice;
    private Boolean newAutomaticApproval;
    private Duration newCancellationDue;
    private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
    private Set<Integer> newAmenities = new HashSet<>();
    private Integer newMinGuests;
    private Integer newMaxGuests;
    private Long originAccommodationId;
}
