package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestUserModificationDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private AddressDTO address;
    private String phone;
    private Map<String, String> settings = new HashMap<>();
}
