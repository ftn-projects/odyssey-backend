package com.example.odyssey.entity.accommodations;

import com.example.odyssey.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accommodation_modification_requests")
public class AccommodationModification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime submissionDate;
    @Enumerated(value = EnumType.ORDINAL)
    private Type type;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
    @Embedded
    private ModificationDetails details;
    @ManyToOne
    private Accommodation accommodation;

    public enum Type {CREATE, UPDATE}

    public enum Status {PENDING, ACCEPTED, DECLINED}

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModificationDetails {
        private String newTitle;
        private String newDescription;
        @Enumerated(value = EnumType.ORDINAL)
        private Accommodation.Type newAccommodationType;
        @Embedded
        private Address newAddress;
        private Double newDefaultPrice;
        private Boolean newAutomaticApproval;
        private Duration newCancellationDue;
        @ElementCollection
        private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "accommodation_request_has_amenity", joinColumns = @JoinColumn(name = "request_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "amenity_id", referencedColumnName = "id"))
        private Set<Amenity> newAmenities = new HashSet<>();
        private Integer newMinGuests;
        private Integer newMaxGuests;
        @ElementCollection
        private Set<String> newImages = new HashSet<>();
    }
}
