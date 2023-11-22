package com.example.odyssey.entity.reviews;

import com.example.odyssey.entity.users.Host;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "host_reviews")
public class HostReview extends Review {
    private Host host;
}
