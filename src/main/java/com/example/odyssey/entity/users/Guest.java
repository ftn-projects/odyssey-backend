package com.example.odyssey.entity.users;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    public Guest(User user) {
        this(user.getId(), user.getStatus(), user.getName(), user.getSurname(), user.getEmail(),
                user.getPassword(), user.getAddress(), user.getPhone(), user.getProfileImage(),
                user.getSettings(), user.getRoles(), new HashSet<>(), user.getUsername());
    }

    public Guest(Long id, AccountStatus status, String name, String surname,
                 String email, String password, Address address, String phone, String profileImage,
                 NotificationSettings settings, List<Role> roles, Set<Accommodation> favorites, String username) {
        super(id, status, name, surname, email, password, address, phone, profileImage, settings, LocalDateTime.now(), username, roles);
        this.favorites = favorites;
    }
}
