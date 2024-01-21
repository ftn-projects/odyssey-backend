package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("R")
@SQLDelete(sql = "UPDATE host_reviews SET deleted = true WHERE id=?")
@SQLDelete(sql = "UPDATE accommodation_reviews SET deleted = true WHERE id=?")
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private String comment;
    private Status status;
    private LocalDateTime submissionDate;
    @ManyToOne
    private Guest submitter;
    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    public enum Status {REQUESTED, DECLINED, ACCEPTED, REPORTED}

    public Review(Double rating, String comment, Status status, LocalDateTime submissionDate, Guest submitter) {
        this.rating = rating;
        this.comment = comment;
        this.status = status;
        this.submissionDate = submissionDate;
        this.submitter = submitter;
    }
}