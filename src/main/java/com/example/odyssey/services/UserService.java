package com.example.odyssey.services;

import com.example.odyssey.entity.TimeSlot;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.exceptions.IncorrectPasswordException;
import com.example.odyssey.repositories.ReservationRepository;
import com.example.odyssey.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() { return userRepository.findAll(); }
    public User find(Long id) { return userRepository.findUserById(id); }
    public User register(User user) { return userRepository.save(user); }
    public User update(User updated) {
        User user = userRepository.findUserById(updated.getId());
        user = updated;
        return userRepository.save(updated);
    }
    public void updatePassword(Long id, String oldPassword, String newPassword) throws IncorrectPasswordException {
        User user = userRepository.findUserById(id);
        if (!oldPassword.equals(user.getPassword()))
            throw new IncorrectPasswordException();

        user.setPassword(newPassword);
        userRepository.save(user);
    }
}
