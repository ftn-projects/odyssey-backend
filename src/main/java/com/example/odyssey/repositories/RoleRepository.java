package com.example.odyssey.repositories;

import com.example.odyssey.entity.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
    List<Role> findByName(String name);
}
