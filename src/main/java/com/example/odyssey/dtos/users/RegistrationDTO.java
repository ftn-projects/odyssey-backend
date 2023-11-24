package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDTO extends UserDTO{
    private Long id;
    private User.Role role;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String phone;
    private AddressDTO address;
    private Map<String, String> settings;
    private String bio;

    public RegistrationDTO(User user){this(user.getId(), user.getRole(),user.getEmail(),user.getPassword(),user.getName(),
            user.getSurname(),user.getPhone(),new AddressDTO(user.getAddress()), user.getSettings(),"");}

    public RegistrationDTO(Host host){this(host.getId(), host.getRole(),host.getEmail(),host.getPassword(),host.getName(),
            host.getSurname(),host.getPhone(),new AddressDTO(host.getAddress()), host.getSettings(),host.getBio());}
}
