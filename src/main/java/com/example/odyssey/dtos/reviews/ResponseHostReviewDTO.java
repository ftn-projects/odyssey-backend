package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.UserDTO;
import com.example.odyssey.entity.reviews.HostReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseHostReviewDTO extends ResponseReviewDTO {
    private UserDTO host;

    public ResponseHostReviewDTO(HostReview review) {
        super(review);
        host = new UserDTO(review.getHost());
    }
}
