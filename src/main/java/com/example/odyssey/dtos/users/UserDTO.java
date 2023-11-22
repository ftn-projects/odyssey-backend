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
public class UserDTO {
    private Long id;
    private String name;
    private String surname;

    public UserDTO(User user){
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
    }
}
