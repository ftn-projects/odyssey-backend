package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HostReviewDTO extends ReviewDTO {
    private UserDTO host;

    public HostReviewDTO(Long id, Double rating, String comment, LocalDateTime submissionDate, Review.Status status, UserDTO submitter, UserDTO host) {
        super(id, rating, comment, submissionDate, status, submitter);
        this.host = host;
    }

    public HostReviewDTO(HostReview review) {
        super(review);
        host = new UserDTO(review.getHost());
    }
}
