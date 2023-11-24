package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private User.Role role;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private AddressDTO address;
    private Map<String, String> settings;
    private String bio;
}
