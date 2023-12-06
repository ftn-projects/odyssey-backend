package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class AccommodationSearchDTO extends AccommodationDTO {
    private Double totalPrice;
    private Double averageRating;

    public AccommodationSearchDTO(Long id, String title, String description, Accommodation.Type type, AddressDTO address, Accommodation.PricingType pricing, Set<AmenityDTO> amenities, UserDTO host, Double totalPrice, Double averageRating) {
        //super(id, title, description, type, address, pricing, amenities, host);
        this.totalPrice = totalPrice;
        this.averageRating = averageRating;
    }

    public AccommodationSearchDTO(Accommodation accommodation, Double totalPrice, Double averageRating) {
        super(accommodation);
        this.totalPrice = totalPrice;
        this.averageRating = averageRating;
    }
}
