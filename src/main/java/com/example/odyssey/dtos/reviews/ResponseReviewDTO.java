package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.ResponseUserDTO;
import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ResponseReviewDTO {
    private Long id;
    private Double rating;
    private String comment;
    private LocalDateTime submissionDate;
    private UserDTO submitter;

    public ResponseReviewDTO(Review review) {
        id = review.getId();
        rating = review.getRating();
        comment = review.getComment();
        submissionDate = review.getSubmissionDate();
        submitter = new UserDTO(review.getSubmitter());
    }
}
