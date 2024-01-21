package com.example.odyssey.repositories;

import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @NonNull
    Page<User> findAll(@NonNull Pageable pageable);

    Optional<User> findUserByEmail(String email);

    List<User> findUsersByStatus(User.AccountStatus status);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE (:search IS NULL OR UPPER(CONCAT(u.name, ' ', u.surname)) LIKE %:search%) " +
            "  AND (COALESCE(:roles, NULL) IS NULL OR EXISTS (SELECT 1 FROM u.roles r WHERE r.name IN :roles)) " +
            "  AND (COALESCE(:statuses, NULL) IS NULL OR u.status IN :statuses) " +
            "  AND (:reported IS NULL OR :reported = FALSE OR EXISTS (SELECT 1 FROM UserReport r WHERE r.reported.id = u.id))")
    List<User> findAllByFilters(
            @Param("search") String search,
            @Param("roles") List<String> roles,
            @Param("statuses") List<User.AccountStatus> statuses,
            @Param("reported") Boolean reported);

    @Query("SELECT u " +
            "FROM User u " +
            "WHERE :role IN (SELECT r.name FROM u.roles r)")
    List<User> findAllByRole(String role);
}
