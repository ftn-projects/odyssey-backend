package com.example.odyssey.services;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Role;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.exceptions.InputValidationException;
import com.example.odyssey.repositories.RoleRepository;
import com.example.odyssey.repositories.UserRepository;
import com.example.odyssey.util.EmailUtil;
import com.example.odyssey.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ReservationService reservationService;

    private final String imagesDirPath = "src/main/resources/images/users/";

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException(String.format("User with id '%d' does not exist.", id)));
    }

    public User register(User user, String role) {
        List<Role> roles = roleRepository.findByName(role.toUpperCase());
        roles.add(roleRepository.findByName("USER").get(0));

        user.setRoles(roles);
        user.setStatus(User.AccountStatus.PENDING);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());

        user = userRepository.save(new Guest(user));
        EmailUtil.sendConfirmation(user.getEmail(), user.getName(), user.getId());
        return user;
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new NoSuchElementException(String.format("User with the email '%s' does not exist.", email)));
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = findById(id);

        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new InputValidationException("Current password is incorrect.", "Old password");

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deactivate(Long id) {
        User user = findById(id);

        if (!canDeactivate(user))
            throw new UnsupportedOperationException("Account deactivation failed because you have active reservations.");

        updateAccountStatus(user, User.AccountStatus.DEACTIVATED);
    }

    private boolean canDeactivate(User user) {
        List<Reservation> reservations = new ArrayList<>();

        if (user.hasRole("HOST"))
            reservations = reservationService.findByHost(user.getId());
        else if (user.hasRole("GUEST"))
            reservations = reservationService.findByGuest(user.getId());

        return reservations.stream().noneMatch(r ->
                r.getStatus().equals(Reservation.Status.ACCEPTED) &&
                        r.getTimeSlot().getEnd().isAfter(LocalDateTime.now()));
    }

    public void block(Long id) {
        User user = findById(id);
        updateAccountStatus(user, User.AccountStatus.BLOCKED);
    }

    public void updateAccountStatus(User user, User.AccountStatus status) {
        user.setStatus(status);
        userRepository.save(user);
    }

    public void confirmEmail(Long id) {
        try {
            User user = findById(id);
            if (user.getStatus().equals(User.AccountStatus.PENDING))
                throw new UnsupportedOperationException("User account has already been activated.");

            updateAccountStatus(user, User.AccountStatus.ACTIVE);
        } catch (NoSuchElementException e) {
            throw new UnsupportedOperationException("Invalid email activation link.");
        }
    }

    public List<User> findByStatus(User.AccountStatus status) {
        return userRepository.findUsersByStatus(status);
    }

    public void deleteExpiredAccounts() {
        List<User> users = findByStatus(User.AccountStatus.PENDING);
        for (User u : users) {
            if (!LocalDateTime.now().minusDays(1).isBefore(u.getCreated())) {
                userRepository.delete(u);
            }
        }
    }

    public byte[] getImage(Long id) throws IOException {
        String imageName = findById(id).getProfileImage();
        String imagePath = StringUtils.cleanPath(imagesDirPath + id + "/" + imageName);
        try {
            return Files.readAllBytes(new File(imagePath).toPath());
        } catch (IOException e) {
            throw new IOException(String.format("Image file '%s' not found for the user with id '%d'.", imageName, id));
        }
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        User user = findById(id);

        if (image.getOriginalFilename() == null)
            throw new IOException("Image upload file cannot be found.");

        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);
        ImageUtil.saveImage(uploadDir, "profile.png", image);
        user.setProfileImage("profile.png");
        userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
