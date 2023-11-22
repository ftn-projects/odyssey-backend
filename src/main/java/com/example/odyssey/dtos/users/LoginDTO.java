package com.example.odyssey.dtos.users;

import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    private String email;
    private String password;

    public LoginDTO(User user){
        email = user.getEmail();
        password = user.getPassword();
    }
}
