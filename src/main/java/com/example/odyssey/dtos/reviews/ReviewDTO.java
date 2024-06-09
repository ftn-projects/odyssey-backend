package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.Review;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "Review rating must be set.")
    @Positive(message = "Review rating must be a positive integer.")
    private Double rating;
    @Size(max = 800, message = "Review comment should be shorter than 800 characters.")
    private String comment;
    private LocalDateTime submissionDate;
    private Review.Status status;
    @NotNull(message = "Review submitter must be set.")
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
