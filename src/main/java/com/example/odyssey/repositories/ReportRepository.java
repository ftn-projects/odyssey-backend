package com.example.odyssey.repositories;

import com.example.odyssey.entity.reports.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportRepository extends JpaRepository<UserReport, Long> {
    @Query("SELECT r FROM UserReport r WHERE r.reported.id = :id")
    List<UserReport> findByReportedId(Long id);
}
