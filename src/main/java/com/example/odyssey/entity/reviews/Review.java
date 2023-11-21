package com.example.odyssey.entity.reviews;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Review {
    @Id
    @GeneratedValue
    private Long id;
}
