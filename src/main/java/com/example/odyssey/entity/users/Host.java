package com.example.odyssey.entity.users;

import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "HOST")
public class Host extends User {
    private String bio;
    @OneToMany(mappedBy = "host")
    private Set<Accommodation> accommodations;
}
