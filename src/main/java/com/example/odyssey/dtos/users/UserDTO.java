package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
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

    public UserDTO(User user) {
        this(user.getId(), user.getRole(), user.getEmail(), user.getName(), user.getSurname(),
                user.getPhone(), new AddressDTO(user.getAddress()), user.getSettings(), "");
    }

    public UserDTO(Host host) {
        this(host.getId(), host.getRole(), host.getEmail(), host.getName(), host.getSurname(),
                host.getPhone(), new AddressDTO(host.getAddress()), host.getSettings(), host.getBio());
    }
}
