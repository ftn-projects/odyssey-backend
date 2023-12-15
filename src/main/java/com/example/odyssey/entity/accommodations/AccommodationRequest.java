package com.example.odyssey.entity.accommodations;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Host;
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
@Table(name = "accommodation_requests")
public class AccommodationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime submissionDate;
    private Type type;
    private Status status;
    @Embedded
    private ModificationDetails details;
    @ManyToOne
    private Host host;
    private Long accommodationId;

    public enum Type {CREATE, UPDATE}

    public enum Status {REQUESTED, ACCEPTED, DECLINED}

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModificationDetails {
        private String newTitle;
        private String newDescription;
        private Accommodation.Type newAccommodationType;
        @Embedded
        private Address newAddress;
        private Double newDefaultPrice;
        private Boolean newAutomaticApproval;
        private Duration newCancellationDue;
        @ElementCollection
        private Set<AvailabilitySlot> newAvailableSlots = new HashSet<>();
        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "accommodation_modification_has_amenity", joinColumns = @JoinColumn(name = "modification_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "amenity_id", referencedColumnName = "id"))
        private Set<Amenity> newAmenities = new HashSet<>();
        private Integer newMinGuests;
        private Integer newMaxGuests;
        @ElementCollection
        private Set<String> newImages = new HashSet<>();
    }
}
