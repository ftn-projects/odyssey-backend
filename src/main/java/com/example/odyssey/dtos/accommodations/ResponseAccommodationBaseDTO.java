package com.example.odyssey.dtos.accommodations;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.HostDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseAccommodationBaseDTO {
    private Long id;
    private String title;
    private String description;
    private Accommodation.Type type;
    private AddressDTO address;
    private Accommodation.PricingType pricing;
    private HostDTO host;

    public ResponseAccommodationBaseDTO(Accommodation accommodation) {
        id = accommodation.getId();
        title = accommodation.getTitle();
        description = accommodation.getDescription();
        type = accommodation.getType();
        address = new AddressDTO(accommodation.getAddress());
        pricing = accommodation.getPricing();
        host = new HostDTO(accommodation.getHost());
    }
}
