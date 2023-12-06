package com.example.odyssey.dtos.users;

import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    private Long userId;
    private String oldPassword;
    private String newPassword;
}
