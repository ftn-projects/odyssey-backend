package com.example.odyssey.dtos;

import com.example.odyssey.entity.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    @NotBlank(message = "Street should not be blank.")
    @Size(min = 2, message = "Street should not be shorter than two characters.")
    @Pattern(regexp = "^[\\p{L}\\p{Zs}]+( )[\\p{L}\\p{N}\\p{Zs}]+$", message = "Street should only have letters and numbers.")
    private String street;
    @NotBlank(message = "City should not be blank.")
    @Size(min = 2, message = "City should not be shorter than two characters.")
    @Pattern(regexp = "^[\\p{L}\\p{Zs}]+$", message = "City should only have letters.")
    private String city;
    @NotBlank(message = "Country should not be blank.")
    @Size(min = 2, message = "Country should not be shorter than two characters.")
    @Pattern(regexp = "^[\\p{L}\\p{Zs}]+$", message = "Country should only have letters.")
    private String country;

    public AddressDTO(Address address) {
        street = address.getStreet();
        city = address.getCity();
        country = address.getCountry();
    }
}
