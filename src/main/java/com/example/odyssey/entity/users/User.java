package com.example.odyssey.entity.users;

import com.example.odyssey.entity.accommodations.Address;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private Role role;
    private AccountStatus status;
    private String name;
    private String surname;
    private String email;
    @Embedded
    private Address address;
    private String phone;
    private String profileImage;

    public enum Role {HOST, ADMIN, GUEST};
    public enum AccountStatus {PENDING_ACTIVATION, ACTIVE, BLOCKED, DEACTIVATED};
}
