package com.example.odyssey.services;

import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public void create(UserReport report) {
        reportRepository.save(report);
    }

    public void deleteUserReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<UserReport> findByReportedId(Long id) {
        return reportRepository.findByReportedId(id);
    }
}
