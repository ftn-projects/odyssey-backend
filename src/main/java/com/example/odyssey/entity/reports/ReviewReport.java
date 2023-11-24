package com.example.odyssey.entity.reports;

import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "review_reports")
public class ReviewReport extends Report {
    @ManyToOne
    private Review reportedReview;

    public ReviewReport(Long id, String description, LocalDateTime submissionDate, User submitter, Review reportedReview) {
        super(id, description, submissionDate, submitter);
        this.reportedReview = reportedReview;
    }
}
