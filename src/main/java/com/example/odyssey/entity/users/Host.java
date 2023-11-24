package com.example.odyssey.entity.users;

import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
@DiscriminatorValue(value = "HOST")
public class Host extends User {
    private String bio;
    @OneToMany(mappedBy = "host")
    private Set<Accommodation> accommodations;

    public Host(Long id, Role role, AccountStatus status, String name, String surname, String email, String password, Address address, String phone, String profileImage, Map<String, String> settings, String bio, Set<Accommodation> accommodations) {
        super(id, role, status, name, surname, email, password, address, phone, profileImage, settings);
        this.bio = bio;
        this.accommodations = accommodations;
    }
}
