package com.example.odyssey.entity.notifications;

import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @Transient
    public static final String WELCOME_TITLE = "Welcome to Odyssey";
    @Transient
    public static final String WELCOME_DESCRIPTION = "You can visit home page to browse accommodations of our hosts. Have a great stay :)";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String title;
    protected String description;
    protected LocalDateTime date;
    protected Boolean read;
    protected Type type;
    @ManyToOne
    protected User receiver;

    public Notification(String title, String description, User receiver) {
        this();
        this.title = title;
        this.description = description;
        this.receiver = receiver;
    }

    public enum Type {GENERIC, ACCOMMODATION_REVIEW, HOST_REVIEW, RESERVATION_REQUESTED, RESERVATION_ACCEPTED, RESERVATION_DECLINED, RESERVATION_CANCELLED}
}
