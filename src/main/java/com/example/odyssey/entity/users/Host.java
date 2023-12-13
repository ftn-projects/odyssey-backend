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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    public Host(User user){
        this(user.getId(), user.getStatus(), user.getName(), user.getSurname(), user.getEmail(),
                user.getPassword(), user.getAddress(), user.getPhone(), user.getProfileImage(),
                user.getSettings(),user.getRoles(),"",new HashSet<>());

    }

    public Host(Long id,AccountStatus status, String name, String surname, String email, String password, Address address,
                String phone, String profileImage, User.Settings settings, List<Role> roles, String bio, Set<Accommodation> accommodations) {
        super(id, status, name, surname, email, password, address, phone, profileImage, settings, LocalDateTime.now(), roles);
        this.bio = bio;
        this.accommodations = accommodations;
    }
}
