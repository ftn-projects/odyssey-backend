package com.example.odyssey.entity.reports;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Report {
    @Id
    @GeneratedValue
    private Long id;
}
