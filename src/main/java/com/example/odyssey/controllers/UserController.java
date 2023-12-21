package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.*;
import com.example.odyssey.entity.Address;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.Role;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.services.UserService;
import com.example.odyssey.util.TokenUtil;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<?> findById(@PathVariable Long id, @RequestHeader("Authorization") String authToken) {
        try {
            tokenUtil.validateAccess(authToken, id, true);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        User user = service.find(id);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        UserDTO dto = UserDTOMapper.fromUserToDTO(user);

        if (user.getRoles().get(0).getName().equals("HOST"))
            dto.setBio(((Host) user).getBio());
        return new ResponseEntity<>(dto, HttpStatus.OK);
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
    public ResponseEntity<?> update(@Valid @RequestBody UserDTO dto, @RequestHeader("Authorization") String authToken) {
        try {
            tokenUtil.validateAccess(authToken, dto.getId(), true);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        User user = service.find(dto.getId());
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        Address address = new Address();
        address.setStreet(dto.getAddress().getStreet());
        address.setNumber(dto.getAddress().getNumber());
        address.setCity(dto.getAddress().getCity());
        address.setCountry(dto.getAddress().getCountry());
        user.setAddress(address);

        if (user instanceof Host) ((Host) user).setBio(dto.getBio());

        service.save(user);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDTO dto, @RequestHeader("Authorization") String authToken) {
        try {
            tokenUtil.validateAccess(authToken, dto.getUserId(), true);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        service.updatePassword( dto.getUserId(), dto.getOldPassword(), dto.getNewPassword());
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
    public ResponseEntity<?> deactivate(@PathVariable Long id, @RequestHeader("Authorization") String authToken) {
        try {
            tokenUtil.validateAccess(authToken, id, false);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.UNAUTHORIZED);
        }

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
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        try {
            User u = service.find(id);
            return new ResponseEntity<>(service.getImage(id, u.getProfileImage()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/image/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Long id, @RequestParam("image") MultipartFile imageFile, @RequestHeader("Authorization") String authToken) throws IOException {
        try {
            tokenUtil.validateAccess(authToken, id, true);
        } catch (ValidationException ve) {
            return new ResponseEntity<>(ve.getMessage(), HttpStatus.UNAUTHORIZED);
        }

        service.uploadImage(id, imageFile);
        return new ResponseEntity<>("Image uploaded successfully.", HttpStatus.OK);
    }

    private static List<UserDTO> mapToDTO(List<User> users) {
        return users.stream().map(UserDTO::new).toList();
    }
}
