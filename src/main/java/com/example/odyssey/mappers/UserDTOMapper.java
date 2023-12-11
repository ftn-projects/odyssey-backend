package com.example.odyssey.mappers;

import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.users.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper {
    private static ModelMapper mapper;

    @Autowired
    public UserDTOMapper(ModelMapper mapper) {
        UserDTOMapper.mapper = mapper;
    }

    public static User fromDTOtoUser(UserDTO dto) {
        return  mapper.map(dto, User.class);
    }

    public static User fromRegistrationDTOtoUser(RegistrationDTO dto) {
        User user = fromDTOtoUser(dto);
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserDTO fromUserToDTO(User user) {
        return new UserDTO(user);
    }
}
