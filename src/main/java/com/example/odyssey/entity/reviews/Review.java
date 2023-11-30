package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Review {
    @Id
    private Long id;
    private Double rating;
    private String comment;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;
    private LocalDateTime submissionDate;
    @ManyToOne
    private Guest submitter;

    public enum Status {REQUESTED, DECLINED, ACCEPTED}
}
