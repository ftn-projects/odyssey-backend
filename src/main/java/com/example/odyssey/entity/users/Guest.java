package com.example.odyssey.entity.users;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "GUEST")
public class Guest extends User {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "guest_favourited", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"))
    private Set<Accommodation> favourites;

    public Guest(Long id, Role role, AccountStatus status, String name, String surname,
                 String email, String password, Address address, String phone, String profileImage,
                 User.Settings settings, Set<Accommodation> favourites) {
        super(id, role, status, name, surname, email, password, address, phone, profileImage, settings);
        this.favourites = favourites;
    }
}
