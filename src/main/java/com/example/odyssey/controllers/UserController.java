package com.example.odyssey.controllers;

import com.example.odyssey.dtos.AddressDTO;
import com.example.odyssey.dtos.users.RegistrationDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @GetMapping("/{id}")
    public Guest getGuestById(@PathVariable Long id){
        return new Guest(id,User.Role.GUEST,User.AccountStatus.ACTIVE,"Milan","Stankovic",
                "milan@gmail.com","123", new Address("Ulica",15,"Beograd","Srbija"),
                "0612345678","image.png", new HashMap<>(), new HashSet<>());
    }

    @GetMapping("/{id}")
    public Host getHostById(@PathVariable Long id){
        return new Host(id,User.Role.HOST,User.AccountStatus.ACTIVE,"Rada","Manojlovic",
                "radojka@gmail.com","456", new Address("Ulica",16,"Beograd","Srbija"),
                "0612345678","image.png", new HashMap<>(),"bio",new HashSet<>());
    }

    @GetMapping("/login/{email}/{password}")
    public UserDTO login(@PathVariable String email, @PathVariable String password){
        return new UserDTO(123L, User.Role.HOST, email,
                "Slavica","Cukteras","0622345678",
                new AddressDTO(new Address("Ulica",16,"Beograd","Srbija")),
                new HashMap<>(),"");
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO dto){
        if(dto.getRole().equals(User.Role.HOST)){
            Host host = new Host();
            host.setName(dto.getName());
            host.setSurname(dto.getSurname());
            host.setPhone(dto.getPhone());
            host.setAddress(new Address(dto.getAddress().getStreet(),dto.getAddress().getNumber(),dto.getAddress().getCity(),dto.getAddress().getCountry()));
            host.setSettings(dto.getSettings());

            return new ResponseEntity<>(new UserDTO(host), HttpStatus.OK);
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhone(dto.getPhone());
        user.setAddress(new Address(dto.getAddress().getStreet(),dto.getAddress().getNumber(),dto.getAddress().getCity(),dto.getAddress().getCountry()));
        user.setSettings(dto.getSettings());

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void>  deactivateUser(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setStatus(User.AccountStatus.DEACTIVATED);
        if(id != null){
//            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDTO> blockUser(@PathVariable Long id){
        User user = new User();
        user.setId(id);
        user.setStatus(User.AccountStatus.BLOCKED);
        return new ResponseEntity<>(new UserDTO(user),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegistrationDTO> register(@RequestBody RegistrationDTO dto){
        if(dto.getRole().equals(User.Role.HOST)){
            Host host = new Host();
            host.setId(dto.getId());
            host.setRole(dto.getRole());
            host.setEmail(dto.getEmail());
            host.setPassword(dto.getPassword());
            host.setName(dto.getName());
            host.setSurname(dto.getSurname());
            host.setPhone(dto.getPhone());
            host.setAddress(new Address(dto.getAddress().getStreet(),dto.getAddress().getNumber(),dto.getAddress().getCity(),dto.getAddress().getCountry()));
            host.setSettings(dto.getSettings());
            host.setBio(dto.getBio());

            return new ResponseEntity<>(new RegistrationDTO(host),HttpStatus.CREATED);

        }

        User user = new User();
        user.setId(dto.getId());
        user.setRole(dto.getRole());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhone(dto.getPhone());
        user.setAddress(new Address(dto.getAddress().getStreet(),dto.getAddress().getNumber(),dto.getAddress().getCity(),dto.getAddress().getCountry()));
        user.setSettings(dto.getSettings());

        return new ResponseEntity<>(new RegistrationDTO(user),HttpStatus.CREATED);
    }
}
