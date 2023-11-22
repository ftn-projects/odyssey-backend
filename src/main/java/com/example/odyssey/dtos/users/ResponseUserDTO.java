package com.example.odyssey.dtos.users;

import com.example.odyssey.entity.users.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseUserDTO {
    private Long id;
    private String name;
    private String surname;
    private User.Role role;

    public ResponseUserDTO(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        role = user.getRole();
    }
}
