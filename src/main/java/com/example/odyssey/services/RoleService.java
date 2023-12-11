package com.example.odyssey.services;

import com.example.odyssey.entity.users.Role;
import com.example.odyssey.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role find(Long id){return roleRepository.findRoleById(id);}

    public List<Role> findByName(String name){return roleRepository.findByName(name);}
}
