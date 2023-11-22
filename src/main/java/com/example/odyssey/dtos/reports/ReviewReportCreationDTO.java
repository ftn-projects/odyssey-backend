package com.example.odyssey.dtos.reports;

import com.example.odyssey.dtos.reviews.ReviewCreationDTO;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReportCreationDTO extends ReportCreationDTO{
    private Review reported;

    public ReviewReportCreationDTO(ReviewReport review){
        super();
        reported = review.getReported();
    }
}
