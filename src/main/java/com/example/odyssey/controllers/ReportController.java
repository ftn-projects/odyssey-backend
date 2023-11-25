package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reports.ReportDTO;
import com.example.odyssey.dtos.reports.ReviewReportDTO;
import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.entity.reports.Report;
import com.example.odyssey.entity.reports.ReviewReport;
import com.example.odyssey.entity.reports.UserReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    private final List<ReviewReport> reviewData;
    private final List<UserReport> userData;

    public ReportController() {
        reviewData = new ArrayList<>() {{
            add(new ReviewReport());
            add(new ReviewReport());
            add(new ReviewReport());
            add(new ReviewReport());
            add(new ReviewReport());
        }};
        userData = new ArrayList<>() {{
            add(new UserReport());
            add(new UserReport());
            add(new UserReport());
            add(new UserReport());
            add(new UserReport());
        }};
    }

    @GetMapping("/review")
    public ResponseEntity<List<ReviewReportDTO>> getAllReviewReports() {
        List<ReviewReport> reports = reviewData;

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(ReviewReportDTO::new).toList(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        List<UserReport> reports = userData;

//        service.getAllReviewReports();

        return new ResponseEntity<>(reports.stream().map(UserReportDTO::new).toList(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReviewReportDTO> createReviewReport(@RequestBody ReviewReportDTO reportDTO) {
        ReviewReport report = new ReviewReport();

//        service.saveReviewReport(report);

        return new ResponseEntity<>(new ReviewReportDTO(report), HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<UserReportDTO> createUserReport(@RequestBody UserReportDTO reportDTO) {
        UserReport report = new UserReport();

//        service.saveUserReport(report);

        return new ResponseEntity<>(new UserReportDTO(report), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReportDTO> delete(@PathVariable Long id) {
        Report report = new UserReport();

//        report = service.delete(id);

        ReportDTO reportDTO;
        if (report instanceof ReviewReport)
            reportDTO = new ReviewReportDTO((ReviewReport) report);
        else reportDTO = new UserReportDTO((UserReport) report);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }
}
