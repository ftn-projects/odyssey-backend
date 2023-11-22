package com.example.odyssey.dtos.reviews;

import com.example.odyssey.entity.reviews.HostReview;
import com.example.odyssey.entity.users.Host;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostReviewCreationDTO extends ReviewCreationDTO{
    private Host host;

    public HostReviewCreationDTO(HostReview review){
        super();
        host = review.getHost();
    }
}
