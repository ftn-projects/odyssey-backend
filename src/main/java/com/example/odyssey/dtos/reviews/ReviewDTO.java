package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
        submissionDate = review.getSubmissionDate();
        status = review.getStatus();
        submitter = new UserDTO(review.getSubmitter());
    }
}
