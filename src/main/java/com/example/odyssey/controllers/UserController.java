package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.*;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.exceptions.users.UserNotFoundException;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.services.NotificationService;
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
    private NotificationService notificationService;
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
        return new ResponseEntity<>(mapToDTO(service.getAll()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = service.findById(id);
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

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDTO dto) {
        service.updatePassword(dto.getUserId(), dto.getOldPassword(), dto.getNewPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirmEmail/{id}")
    public ResponseEntity<String> confirmEmail(@PathVariable Long id) {
        User user = service.confirmEmail(id);
        notificationService.create(new Notification(
                Notification.WELCOME_TITLE, Notification.WELCOME_DESCRIPTION, user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        service.deactivate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activate(@PathVariable Long id) {
        service.activate(id);
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
        try {
            User exists = this.service.findByEmail(userDTO.getEmail());
            if (exists != null) return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        } catch (UserNotFoundException ignored) {
        }

        User user = UserDTOMapper.fromRegistrationDTOtoUser(userDTO);

        user = service.register(user, userDTO.getRole());
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.CREATED);
    }

    @GetMapping(value = "/image/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<?> getImage(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(service.getImage(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile imageFile) throws IOException {
        service.uploadImage(id, imageFile);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private static List<UserDTO> mapToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }
}
