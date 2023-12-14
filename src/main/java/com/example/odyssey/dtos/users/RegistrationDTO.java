package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class RegistrationDTO extends UserDTO {
    @NotBlank(message = "Password should not be blank.")
    private String password;
    @Pattern(regexp = "guest|host", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Role should be GUEST or HOST.")
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
