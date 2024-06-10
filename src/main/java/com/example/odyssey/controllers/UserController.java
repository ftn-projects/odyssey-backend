package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.PasswordDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(mapToDTO(service.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> findById(@PathVariable String username) {
        User user = service.findByUsername(username);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO dto) {
        User user = service.findById(dto.getId());

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        Address address = new Address();
        address.setStreet(dto.getAddress().getStreet());
        address.setCity(dto.getAddress().getCity());
        address.setCountry(dto.getAddress().getCountry());
        user.setAddress(address);
        user.setSettings(dto.getSettings());

        if (user instanceof Host) ((Host) user).setBio(dto.getBio());

        user = service.save(user);

        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDTO dto) {
        service.updatePassword(dto.getUsername(), dto.getOldPassword(), dto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        service.activate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable Long id) {
        service.block(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/image/{username}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable String username) throws IOException {
        return new ResponseEntity<>(service.getImage(username), HttpStatus.OK);
    }

    @PostMapping(value = "/image/{username}")
    public ResponseEntity<?> uploadImage(@PathVariable String username, @RequestParam("image") MultipartFile imageFile) throws IOException {
        service.uploadImage(username, imageFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private static List<UserDTO> mapToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }
}
