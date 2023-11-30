package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "host_reviews")
public class HostReview extends Review {
    @ManyToOne
    private Host reviewedHost;

    public HostReview(Long id, Double rating, String comment, Status status, LocalDateTime submissionDate, Guest submitter, Host reviewedHost) {
        super(id, rating, comment, status, submissionDate, submitter);
        this.reviewedHost = reviewedHost;
    }
}
