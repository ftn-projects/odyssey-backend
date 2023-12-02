package com.example.odyssey.entity.reports;

import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review_reports")
public class ReviewReport extends Report {
    @ManyToOne
    private Review review;

    public ReviewReport(Long id, String description, LocalDateTime submissionDate, User submitter, Review review) {
        super(id, description, submissionDate, submitter);
        this.review = review;
    }
}
