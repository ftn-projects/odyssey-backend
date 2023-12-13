package com.example.odyssey.services;

import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.Role;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.repositories.RoleRepository;
import com.example.odyssey.repositories.UserRepository;
import com.example.odyssey.util.EmailUtils;
import com.example.odyssey.util.ImageUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private ReservationRepository reservationRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
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
        user.setRoles(roles);
        user.setStatus(User.AccountStatus.PENDING);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());

        EmailUtils.sendConfirmation(user.getEmail(), user.getName(), user.getId());

        if (roles.get(0).getName().equalsIgnoreCase("HOST"))
            return userRepository.save(new Host(user));
        return userRepository.save(new Guest(user));
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User update(User updated) {
        User user = userRepository.findUserById(updated.getId());
        user = updated;
        return userRepository.save(updated);
    }

    public void updatePassword(Long id, String oldPassword, String newPassword) throws Exception {
        User user = userRepository.findUserById(id);
        if (!oldPassword.equals(user.getPassword()))
            throw new Exception("Current password is incorrect.");

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public void deactivate(Long id) throws Exception {
        User user = userRepository.findUserById(id);
        if (user.getAuthorities().contains("GUEST"))
            throw new Exception("Account deactivation is not possible because you have active reservations.");

        updateAccountStatus(user, User.AccountStatus.DEACTIVATED);
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
        File file = new File(imagePath);
        return Files.readAllBytes(file.toPath());
    }

    public void uploadImage(Long id, MultipartFile image) throws IOException {
        User user = userRepository.findUserById(id);

        if (image.getOriginalFilename() == null)
            throw new IOException("Image is non existing.");

        String uploadDir = StringUtils.cleanPath(imagesDirPath + id);

        ImageUploadUtil.saveImage(uploadDir, "profile.png", image);

        user.setProfileImage("profile.png");
        userRepository.save(user);
    }
}
