package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue
    private Long id;
    private Double rating;
    private String comment;
    private Status status;
    private LocalDateTime submissionDate;
    @OneToOne
    private Guest guest;
    public enum Status{REQUESTED, DECLINED, CANCELLED, ACCEPTED};
}
