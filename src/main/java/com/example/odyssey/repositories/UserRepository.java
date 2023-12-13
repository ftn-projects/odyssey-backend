package com.example.odyssey.repositories;

import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull Page<User> findAll(@NonNull Pageable pageable);
    User findUserById(Long id);
    User findUserByEmail(String name);
    List<User> findUsersByStatus(User.AccountStatus status);
}
