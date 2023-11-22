package com.example.odyssey.entity.reports;

import com.example.odyssey.entity.reviews.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review_reports")
public class ReviewReport extends Report {
    @ManyToOne
    private Review reported;
}
