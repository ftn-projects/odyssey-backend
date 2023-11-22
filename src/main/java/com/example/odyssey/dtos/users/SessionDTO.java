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
public class SessionDTO extends UserDTO{
    private User.Role role;

    public SessionDTO(User user){
        super();
        role = user.getRole();
    }
}
