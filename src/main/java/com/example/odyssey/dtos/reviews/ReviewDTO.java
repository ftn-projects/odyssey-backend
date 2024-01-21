package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Double rating;
    private String comment;
    private LocalDateTime submissionDate;
    private Review.Status status;
    private UserDTO submitter;

    public ReviewDTO(Review review) {
        id = review.getId();
        rating = review.getRating();
        comment = review.getComment();
        submissionDate = review.getSubmissionDate().truncatedTo(ChronoUnit.SECONDS);
        status = review.getStatus();
        submitter = new UserDTO(review.getSubmitter());
    }
}
