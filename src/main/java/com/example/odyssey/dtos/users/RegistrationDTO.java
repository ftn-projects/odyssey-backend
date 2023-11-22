package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO {
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private AddressDTO address;

    public RegistrationDTO(User user){
        email = user.getEmail();
        password = user.getPassword();
        name = user.getName();
        surname = user.getSurname();
        phone = user.getPhone();
        address = new AddressDTO(user.getAddress());
    }
}
