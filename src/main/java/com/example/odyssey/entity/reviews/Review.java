package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(columnDefinition = "serial")
    private Long id;
    private Double rating;
    private String comment;
    private Status status;
    private LocalDateTime submissionDate;
    @ManyToOne
    private Guest submitter;

    public Review() {

    }

    public enum Status {REQUESTED, DECLINED, ACCEPTED}

    public Review(Double rating, String comment, Status status, LocalDateTime submissionDate, Guest submitter) {
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.submissionDate = submissionDate;
        this.submitter = submitter;
    }
}
