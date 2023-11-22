package com.example.odyssey.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {

        // GET AllReports()
        // GET AllReports(String type)
        // GET AllUserReports()
        // GET GetAllUserReports(String type)
        // GET AllReviewReports()
        // GET AllReviewReports(String type)
        // GET ReportsByUser(Long userID)
        // GET ReportByUserAndType(Long userID, String type)
        // POST CreateUserReport(RequestUserReportDTO report)
        // POST CreateReviewReport(RequestReviewReportDTO report)
        // PUT UpdateReport(RequestReportDTO report)
        // DELETE DeleteReport(Long reportID)
}
