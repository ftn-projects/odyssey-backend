package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.entity.accommodations.Amenity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDTO {
    @Positive
    private Long id;
    @NotBlank(message = "Title should not be blank.")
    private String title;

    public AmenityDTO(Amenity amenity) {
        this.id = amenity.getId();
        this.title = amenity.getTitle();
    }
}
