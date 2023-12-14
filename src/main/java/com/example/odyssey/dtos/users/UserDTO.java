package com.example.odyssey.dtos.users;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull
    private Long id;
    @Email(message = "Email should be well formed.")
    @Size(max = 64, message = "Email should be shorter than 64 characters.")
    private String email;
    @NotBlank
    @Size(min = 2, message = "Name should not be shorter than 2 characters.")
    private String name;
    @NotBlank
    @Size(min = 2, message = "Surname should not be shorter than 2 characters.")
    private String surname;
    @NotBlank
    @Pattern(regexp = "^(\\(\\+\\d{1,3}\\)( )?)?\\d{7,15}$", message = "Phone should be of pattern: (+111)000000")
    private String phone;
    @NotNull
    private AddressDTO address;
    private User.Settings settings;
    @Size(max = 200, message = "Bio should not be longer than 200 characters.")
    private String bio;

    public UserDTO(User user) {
        this(user.getId(), user.getEmail(), user.getName(), user.getSurname(),
                user.getPhone(), new AddressDTO(user.getAddress()), user.getSettings(), "");
    }

    public UserDTO(Host host) {
        this(host.getId(), host.getEmail(), host.getName(), host.getSurname(),
                host.getPhone(), new AddressDTO(host.getAddress()), host.getSettings(), host.getBio());
    }
}
