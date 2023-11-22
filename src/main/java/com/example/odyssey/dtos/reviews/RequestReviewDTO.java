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
public class RequestReviewDTO {
    private Double rating;
    private String comment;
    private Review.Status status;
    private LocalDateTime submissionDate;
    private Long submitterId;
}
