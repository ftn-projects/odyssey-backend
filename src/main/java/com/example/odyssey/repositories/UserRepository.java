package com.example.odyssey.repositories;

import com.example.odyssey.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull Page<User> findAll(@NonNull Pageable pageable);
    Optional<User> findUserByEmail(String email);
    List<User> findUsersByStatus(User.AccountStatus status);
}
