package com.example.odyssey.dtos.reviews;

import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Guest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreationDTO {
    private Double rating;
    private String comment;
    private Review.Status status;
    private LocalDateTime submissionDate;
    private Guest submitter;

    public ReviewCreationDTO(Review review){
        rating = review.getRating();
        comment = review.getComment();
        status = review.getStatus();
        submissionDate = review.getSubmissionDate();
        submitter = review.getSubmitter();
    }
}
