package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.entity.accommodations.Amenity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDTO {
    private Long id;
    private String title;

    public AmenityDTO(Amenity amenity) {
        this.id = amenity.getId();
        this.title = amenity.getTitle();
    }
}
