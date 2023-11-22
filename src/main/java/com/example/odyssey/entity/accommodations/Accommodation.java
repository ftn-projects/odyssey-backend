package com.example.odyssey.entity.accommodations;

import com.example.odyssey.entity.users.Host;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation {
    @Id
    @GeneratedValue // koristi AUTO strategiju
    private Long id;
    private String title;
    private String description;
    private Type type;
    @Embedded // nema posebne tabele vec se ugradjuje u klasu
    private Address address;
    private PricingType pricing;
    private Double defaultPrice;
    private Boolean automaticApproval;
    private Duration cancellationDue;
    @ElementCollection // kolekcija tipova koji su primitivni/embeddable (izbegava one to many)
    private Set<AvailabilitySlot> availableSlots;
    @ManyToMany
    private Set<Amenity> amenities;
    private Integer minGuests;
    private Integer maxGuests;
    @ElementCollection
    private Set<String> images;
    @ManyToOne
    private Host host;
    public enum Type {APARTMENT, ROOM, HOUSE} // ako je usko vezana uz klasu najbolje da bude u njoj

    public enum PricingType {PER_PERSON, PER_ACCOMMODATION}
}
