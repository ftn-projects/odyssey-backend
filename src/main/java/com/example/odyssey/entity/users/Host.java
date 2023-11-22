package com.example.odyssey.entity.users;

import com.example.odyssey.entity.accommodations.Accommodation;
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
public class Host extends User{
    @OneToMany(mappedBy="host")
    private Set<Accommodation> accommodations;
}
