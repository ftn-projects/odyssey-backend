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
public class RegistrationDTO extends UserDTO {
    private String password;
    private String role;

    public RegistrationDTO(User user, String password, String role) {
        super(user);
        this.password = password;
        this.role = role;
    }

    public RegistrationDTO(Host host, String password, String role) {
        super(host);
        this.password = password;
        this.role = role;
    }
}
