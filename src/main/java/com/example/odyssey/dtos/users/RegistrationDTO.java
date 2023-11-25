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

    public RegistrationDTO(User user, String password) {
        super(user);
        this.password = password;
    }

    public RegistrationDTO(Host host, String password) {
        super(host);
        this.password = password;
    }
}
