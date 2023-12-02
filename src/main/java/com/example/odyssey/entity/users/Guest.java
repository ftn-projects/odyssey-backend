package com.example.odyssey.entity.users;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue(value = "GUEST")
public class Guest extends User {
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "guest_favourited", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "accommodation_id", referencedColumnName = "id"))
    private Set<Accommodation> favorites;

    public Guest(Long id, Role role, AccountStatus status, String name, String surname,
                 String email, String password, Address address, String phone, String profileImage,
                 User.Settings settings, Set<Accommodation> favorites) {
        super(id, role, status, name, surname, email, password, address, phone, profileImage, settings);
        this.favorites = favorites;
    }
}
