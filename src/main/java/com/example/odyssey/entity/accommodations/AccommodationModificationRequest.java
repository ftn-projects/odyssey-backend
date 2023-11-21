package com.example.odyssey.entity.accommodations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationModificationRequest {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime submissionDate;
    private Type type;
    private String newTitle;
    private String newDescription;
    private Accommodation.Type newAccommodationType;
    @Embedded
    private Address newAddress;
    private Double newDefaultPrice;
    private Boolean newAutomaticApproval;
    private Duration newCancellationDue;
    @ElementCollection
    private Set<AvailabilitySlot> newAvailableSlots;
    @ManyToMany
    private Set<Amenity> newAmenities;
    private Integer newMinGuests;
    private Integer newMaxGuests;
    @ElementCollection
    private Set<String> newImages;

    public enum Type {CREATE, UPDATE}
}
