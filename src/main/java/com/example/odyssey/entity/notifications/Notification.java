package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "notification_type", discriminatorType = DiscriminatorType.STRING)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String text;
    @ManyToOne
    private User receiver;
}
