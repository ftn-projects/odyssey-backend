package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestRegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private AddressDTO address;
}
