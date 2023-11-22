package com.example.odyssey.entity.users;

import com.example.odyssey.entity.accommodations.Accommodation;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Guest extends User{
    @ManyToMany
    private Set<Accommodation> favourites;
}
