package com.example.odyssey.entity.users;

import com.example.odyssey.entity.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_role", discriminatorType = DiscriminatorType.STRING)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Role role;
    @Enumerated(value = EnumType.ORDINAL)
    private AccountStatus status;
    private String name;
    private String surname;
    private String email;
    private String password;
    @Embedded
    private Address address;
    private String phone;
    private String profileImage;
    @Embedded
    private Settings settings = new Settings();

    public enum Role {ADMIN, HOST, GUEST}

    public enum AccountStatus {PENDING, ACTIVE, BLOCKED, DEACTIVATED}

    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Settings {
        private Boolean ReservationNotification_On = true;
        private Boolean HostNotification_On = true;
    }
}
