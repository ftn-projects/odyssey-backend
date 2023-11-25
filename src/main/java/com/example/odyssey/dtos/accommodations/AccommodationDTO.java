package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationDTO {
    private Long id;
    private String title;
    private String description;
    private Accommodation.Type type;
    private AddressDTO address;
    private Accommodation.PricingType pricing;
    private Set<AmenityDTO> amenities = new HashSet<>();
    private UserDTO host;

    public AccommodationDTO(Accommodation accommodation) {
        id = accommodation.getId();
        title = accommodation.getTitle();
        description = accommodation.getDescription();
        type = accommodation.getType();
        address = new AddressDTO(accommodation.getAddress());
        pricing = accommodation.getPricing();
        accommodation.getAmenities().forEach((a) -> amenities.add(new AmenityDTO(a)));
        host = new UserDTO(accommodation.getHost());
    }
}
