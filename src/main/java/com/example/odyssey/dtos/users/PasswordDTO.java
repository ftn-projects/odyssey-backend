package com.example.odyssey.dtos.users;

import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordDTO {
    @NotNull
    @Positive(message = "Id should be a natural positive number.")
    private Long userId;
    private String oldPassword;
    @NotBlank(message = "Password should not be blank.")
    @Size(min = 4, message = "Password should be at least 4 characters long.")
    private String newPassword;
}
