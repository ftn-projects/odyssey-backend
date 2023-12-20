package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reports.ReportDTO;
import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.Report;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.mappers.ReportDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/review")
    public ResponseEntity<?> getAllReviewReports() {
        List<ReviewReport> reports = new ArrayList<>();

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(ReviewReportDTO::new).toList(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<?> getAllUserReports() {
        List<UserReport> reports = new ArrayList<>();

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(UserReportDTO::new).toList(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/review")
    public ResponseEntity<?> createReviewReport(@RequestBody ReviewReportDTO reportDTO) {
        ReviewReport report = ReportDTOMapper.fromDTOtoReviewReport(reportDTO);

//        service.saveReviewReport(report);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<?> createUserReport(@RequestBody UserReportDTO reportDTO) {
        UserReport report = ReportDTOMapper.fromDTOtoUserReport(reportDTO);

//        service.saveUserReport(report);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean found = true;
//        report = service.delete(id);

        if (found)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
