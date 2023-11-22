package com.example.odyssey.entity.accommodations;

import com.example.odyssey.entity.users.Host;
import jakarta.persistence.*;
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
@Table(name = "accommodations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(value = EnumType.ORDINAL)
    private Type type;
    @Embedded
    private Address address;
    @Enumerated(value = EnumType.ORDINAL)
    private PricingType pricing;
    private Double defaultPrice;
    private Boolean automaticApproval;
    private Duration cancellationDue;
    @ElementCollection
    private Set<AvailabilitySlot> availableSlots = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "accommodation_has_amenity", joinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "amenity_id", referencedColumnName = "id"))
    private Set<Amenity> amenities = new HashSet<>();
    private Integer minGuests;
    private Integer maxGuests;
    @ElementCollection
    private Set<String> images = new HashSet<>();
    @ManyToOne
    private Host host;

    public enum Type {APARTMENT, ROOM, HOUSE} // ako je usko vezana uz klasu najbolje da bude u njoj

    public enum PricingType {PER_PERSON, PER_ACCOMMODATION}
}
