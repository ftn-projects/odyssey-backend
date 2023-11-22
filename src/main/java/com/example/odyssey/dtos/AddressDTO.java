package com.example.odyssey.dtos;

import com.example.odyssey.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private String street;
    private Integer number;
    private String city;
    private String country;

    public AddressDTO(Address address){
        street = address.getStreet();
        number = address.getNumber();
        city = address.getCity();
        country = address.getCountry();
    }
}
