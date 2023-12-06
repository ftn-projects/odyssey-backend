package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    private final List<User> data = DummyData.getUsers();

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> users = service.getAll();

        if (users.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapToDTO(users),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = service.find(id);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }
// post
    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        User user = data.get(1);

//        user = service.login(email, password);
        if(user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        User user = UserDTOMapper.fromDTOtoUser(userDTO);

        user = service.update(user);
        if (user == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        User user = data.get(2);

//        user = service.deactivate(id);
        user.setStatus(User.AccountStatus.DEACTIVATED);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> block(@PathVariable Long id) {
        User user = data.get(1);

//        user = service.block(id);
        user.setStatus(User.AccountStatus.BLOCKED);
        if (user == null) return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationDTO userDTO) {
        User user = UserDTOMapper.fromRegistrationDTOtoUser(userDTO);

        user = service.register(user);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.CREATED);
    }

    private static List<UserDTO> mapToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }
}
