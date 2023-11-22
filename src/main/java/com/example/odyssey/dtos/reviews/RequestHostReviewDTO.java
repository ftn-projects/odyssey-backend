package com.example.odyssey.dtos.reviews;

import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RequestHostReviewDTO extends RequestReviewDTO {
    private Long hostId;

    public RequestHostReviewDTO(Double rating, String comment, Review.Status status, LocalDateTime submissionDate, Long submitterId, Long hostId) {
        super(rating, comment, status, submissionDate, submitterId);
        this.hostId = hostId;
    }
}
