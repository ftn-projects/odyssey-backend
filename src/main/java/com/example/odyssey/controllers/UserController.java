package com.example.odyssey.controllers;

import com.example.odyssey.dtos.users.*;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.UserDTOMapper;
import com.example.odyssey.util.TokenUtils;
import com.example.odyssey.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> users = service.getAll();

        if (users.isEmpty()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(mapToDTO(users), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = service.find(id);
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
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getAuthorities());
        long expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();

            return new ResponseEntity<>("You successfully logged out!", HttpStatus.OK);
        }
        return new ResponseEntity<>("User is not authenticated!", HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserDTO userDTO) {
        User user = UserDTOMapper.fromDTOtoUser(userDTO);

        user = service.update(user);
        if (user == null) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(UserDTOMapper.fromUserToDTO(user), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordDTO passwordDTO) {
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

    @DeleteMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivate(@PathVariable Long id) {
        try {
            service.deactivate(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/block/{id}")
    public ResponseEntity<?> block(@PathVariable Long id) {
        service.block(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
