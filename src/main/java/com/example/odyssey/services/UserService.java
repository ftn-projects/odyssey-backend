package com.example.odyssey.services;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.Role;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.repositories.RoleRepository;
import com.example.odyssey.repositories.UserRepository;
import com.example.odyssey.util.EmailUtil;
import com.example.odyssey.util.ImageUtil;
import com.example.odyssey.util.TokenUtil;
import com.example.odyssey.validation.FieldValidationException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;

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

    public User find(Long id) {
        return userRepository.findUserById(id);
    }

    public User register(User user, String role) {
        if (role.equalsIgnoreCase("ADMIN"))
            throw new IllegalArgumentException("Registering a new Admin is forbidden");
        List<Role> roles = roleRepository.findByName(role.toUpperCase());
        roles.add(roleRepository.findByName("USER").get(0));
        user.setRoles(roles);
        user.setStatus(User.AccountStatus.PENDING);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());

        User saved;
        if (roles.get(0).getName().equalsIgnoreCase("HOST"))
            saved = userRepository.save(new Host(user));
        else saved = userRepository.save(new Guest(user));

        EmailUtil.sendConfirmation(saved.getEmail(), saved.getName(), saved.getId());
        return saved;
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) {
        var encoder = new BCryptPasswordEncoder();

        User user = userRepository.findUserById(id);

        if (!encoder.matches(oldPassword, user.getPassword()))
            throw new FieldValidationException("Current password is incorrect.", "Old password");

        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deactivate(Long id) throws Exception {
        User user = userRepository.findUserById(id);

        if (user.getAuthorities().contains("GUEST") &&
                !filterActiveReservations(reservationService.findByGuest(user.getId())).isEmpty())
            throw new Exception("Account deactivation is not possible because you have active reservations.");
        if (user.getAuthorities().contains("HOST") &&
                !filterActiveReservations(reservationService.findByHost(user.getId())).isEmpty())
            throw new Exception("Account deactivation is not possible because you have active reservations.");

        updateAccountStatus(user, User.AccountStatus.DEACTIVATED);
    }

    public List<Reservation> filterActiveReservations(List<Reservation> reservations) {
        return reservations.stream().filter(r ->
                r.getStatus().equals(Reservation.Status.ACCEPTED) && r.getTimeSlot().getEnd().isAfter(LocalDateTime.now())).toList();
    }

    public void block(Long id) {
        User user = userRepository.findUserById(id);
        updateAccountStatus(user, User.AccountStatus.BLOCKED);
    }

    public void updateAccountStatus(User user, User.AccountStatus status) {
        user.setStatus(status);
        userRepository.save(user);
    }

    public void confirmEmail(Long id) {
        User user = userRepository.findUserById(id);
        if (user == null || !user.getStatus().equals(User.AccountStatus.PENDING))
            throw new IllegalArgumentException("Activation link has expired.");
        updateAccountStatus(user, User.AccountStatus.ACTIVE);
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

    public byte[] getImage(Long id, String imageName) throws IOException {
        String imagePath = StringUtils.cleanPath(imagesDirPath + id + "/" + imageName);
        try {
            return Files.readAllBytes(new File(imagePath).toPath());
        } catch (IOException e) {
            return Files.readAllBytes(new File(imagesDirPath + "deafult_image.png").toPath());
        }
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        User user = userRepository.findUserById(id);

        if (image.getOriginalFilename() == null)
            throw new IOException("Image is non existing.");

        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);

        ImageUtil.saveImage(uploadDir, "profile.png", image);

        user.setProfileImage("profile.png");
        userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
