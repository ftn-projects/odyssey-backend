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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {
//    @Autowired
//    private ReportService service;
//
//    @Autowired
//    public ReportController(ReportService service) {
//        this.service = service;
//    }

    private final List<ReviewReport> reviewData = DummyData.getReviewReports();
    private final List<UserReport> userData = DummyData.getUserReports();

    @GetMapping("/review")
    public ResponseEntity<?> getAllReviewReports() {
        List<ReviewReport> reports = reviewData;

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(ReviewReportDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getAllUserReports() {
        List<UserReport> reports = userData;

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(UserReportDTO::new).toList(), HttpStatus.OK);
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReviewReport(@RequestBody ReviewReportDTO reportDTO) {
        ReviewReport report = ReportDTOMapper.fromDTOtoReviewReport(reportDTO);

//        service.saveReviewReport(report);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/user")
    public ResponseEntity<?> createUserReport(@RequestBody UserReportDTO reportDTO) {
        UserReport report = ReportDTOMapper.fromDTOtoUserReport(reportDTO);

//        service.saveUserReport(report);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean found = reviewData.stream().anyMatch((r) -> r.getId().equals(id));
//        report = service.delete(id);

        if (found)
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
