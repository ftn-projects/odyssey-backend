package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
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

    private final List<User> data;

    public static List<User> generateDataUsers() {
        return new ArrayList<>() {{
            add(new Guest());
            add(new Host());
            add(new Guest());
            add(new Guest());
            add(new User());
        }};
    }

    public UserController() {
        data = generateDataUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = data.get(0);

//        user = service.findById(id);

        UserDTO userDTO;
        if (user.getRole().equals(User.Role.HOST))
            userDTO = new UserDTO((Host) user);
        else userDTO = new UserDTO(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
//        TODO Guest g = new Guest(id, User.Role.GUEST, User.AccountStatus.ACTIVE, "Milan", "Stankovic",
//                "milan@gmail.com", "123", new Address("Ulica", 15, "Beograd", "Srbija"),
//                "0612345678", "image.png", new HashMap<>(), new HashSet<>());
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<UserDTO> login(@PathVariable String email, @PathVariable String password) {
        User user = data.get(1);

//        user = service.login(email, password);

        UserDTO dto;
        if (user.getRole().equals(User.Role.HOST))
            dto = new UserDTO((Host) user);
        else dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
//        TODO new UserDTO(123L, User.Role.HOST, email,
//                "Slavica", "Cukteras", "0622345678",
//                new AddressDTO(new Address("Ulica", 16, "Beograd", "Srbija")),
//                new HashMap<>(), "");
    }

    @PutMapping
    public ResponseEntity<UserDTO> update(@RequestBody UserDTO userDTO) {
        User user = data.get(2);

//        user = service.update(user);

        UserDTO dto;
        if (user.getRole().equals(User.Role.HOST))
            dto = new UserDTO((Host) user);
        else dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deactivate(@PathVariable Long id) {
        User user = data.get(2);

//        user = service.deactivate(id);
        user.setStatus(User.AccountStatus.DEACTIVATED);

        UserDTO dto;
        if (user.getRole().equals(User.Role.HOST))
            dto = new UserDTO((Host) user);
        else dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> block(@PathVariable Long id) {
        User user = data.get(1);

//        user = service.block(id);
        user.setStatus(User.AccountStatus.BLOCKED);

        UserDTO dto;
        if (user.getRole().equals(User.Role.HOST))
            dto = new UserDTO((Host) user);
        else dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody RegistrationDTO userDTO) {
        User user = data.get(3);

//        user = service.register(user);

        UserDTO dto;
        if (user.getRole().equals(User.Role.HOST))
            dto = new UserDTO((Host) user);
        else dto = new UserDTO(user);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
