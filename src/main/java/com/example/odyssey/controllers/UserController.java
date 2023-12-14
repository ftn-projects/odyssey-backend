package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.*;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.services.UserService;
import com.example.odyssey.util.TokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> users = service.getAll();

        if (users.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapToDTO(users), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = service.find(id);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = service.findByEmail(email);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtil.generateToken(user.getId(), user.getUsername(), user.getAuthorities());
        long expiresIn = tokenUtil.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO userDTO) {
        User user = service.find(userDTO.getId());
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        Address address = new Address();
        address.setStreet(userDTO.getAddress().getStreet());
        address.setNumber(userDTO.getAddress().getNumber());
        address.setCity(userDTO.getAddress().getCity());
        address.setCountry(userDTO.getAddress().getCountry());
        user.setAddress(address);

        if (user instanceof Host) ((Host) user).setBio(userDTO.getBio());

        service.save(user);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDTO passwordDTO) {
        try {
            service.updatePassword(
                    passwordDTO.getUserId(),
                    passwordDTO.getOldPassword(),
                    passwordDTO.getNewPassword());
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirmEmail/{id}")
    public ResponseEntity<String> confirmEmail(@PathVariable Long id) {
        try {
            service.confirmEmail(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            service.deactivate(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable Long id) {
        service.block(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDTO userDTO) {
        User exists = this.service.findByEmail(userDTO.getEmail());
        if (exists != null) return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);

        User user = UserDTOMapper.fromRegistrationDTOtoUser(userDTO);

        user = service.register(user, userDTO.getRole());
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {
        User u = service.find(id);
        return new ResponseEntity<>(service.getImage(id, u.getProfileImage()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile imageFile) throws IOException {
        service.uploadImage(id, imageFile);
        return new ResponseEntity<>("Image uploaded successfully.", HttpStatus.OK);
    }

    private static List<UserDTO> mapToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }
}
