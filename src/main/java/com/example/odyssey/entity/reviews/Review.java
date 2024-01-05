package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static jakarta.persistence.InheritanceType.TABLE_PER_CLASS;

@Entity
@Getter
@Setter
@Inheritance(strategy = TABLE_PER_CLASS)
public abstract class Review {
    @Id
    @SequenceGenerator(name = "reviewIDGenerator", sequenceName = "reviewSeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviewIDGenerator")
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
