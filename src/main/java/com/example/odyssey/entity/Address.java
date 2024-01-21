package com.example.odyssey.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Column(name = "address_street")
    private String street;
    @Column(name = "address_city")
    private String city;
    @Column(name = "address_country")
    private String country;

    public Address(Address address) {
        this.street = address.getStreet();
        this.city = address.getCity();
        this.country = address.getCountry();
    }
}
