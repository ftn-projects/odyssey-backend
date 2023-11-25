package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
//    @Autowired
//    private UserService service;
//
//    @Autowired
//    public UserController(UserService service) {
//        this.service = service;
//    }

    private final List<User> data = DummyData.getUsers();

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = data.get(Math.toIntExact(id-1));

//        user = service.findById(id);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<UserDTO> login(@PathVariable String email, @PathVariable String password) {
        User user = data.get(1);

//        user = service.login(email, password);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        User user = UserDTOMapper.fromDTOtoUser(userDTO);

//        user = service.update(user);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deactivate(@PathVariable Long id) {
        User user = data.get(2);

//        user = service.deactivate(id);
        user.setStatus(User.AccountStatus.DEACTIVATED);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> block(@PathVariable Long id) {
        User user = data.get(1);

//        user = service.block(id);
        user.setStatus(User.AccountStatus.BLOCKED);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO userDTO) {
        User user = UserDTOMapper.fromRegistrationDTOtoUser(userDTO);

//        user = service.register(user);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.CREATED);
    }
}
