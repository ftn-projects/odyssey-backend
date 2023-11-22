package com.example.odyssey.dtos.reviews;

import com.example.odyssey.dtos.users.ResponseUserDTO;
import com.example.odyssey.entity.reviews.HostReview;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseHostReviewDTO extends ResponseReviewDTO {
    private ResponseUserDTO host;

    public ResponseHostReviewDTO(HostReview review) {
        super(review);
        host = new ResponseUserDTO(review.getHost());
    }
}
