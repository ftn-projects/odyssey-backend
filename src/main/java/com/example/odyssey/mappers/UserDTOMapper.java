package com.example.odyssey.mappers;

import com.example.odyssey.dtos.accommodations.AccommodationDTO;
import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.accommodations.Accommodation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
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
        return switch (dto.getRole()) {
            case HOST -> mapper.map(dto, Host.class);
            case GUEST -> mapper.map(dto, Guest.class);
            default -> mapper.map(dto, User.class);
        };
    }

    public static User fromRegistrationDTOtoUser(RegistrationDTO dto) {
        User user = fromDTOtoUser(dto);
        user.setPassword(dto.getPassword());
        return user;
    }

    public static UserDTO fromUserToDTO(User user) {
        if (user.getRole().equals(User.Role.HOST))
            return new UserDTO((Host) user);
        return new UserDTO(user);
    }
}
