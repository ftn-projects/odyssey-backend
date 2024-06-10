package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reports.UserReportSubmissionDTO;
import com.example.odyssey.dtos.users.UserWithReportsDTO;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.users.Guest;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.services.ReportService;
import com.example.odyssey.services.ReviewService;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/reports")
public class ReportController {
    @Autowired
    private UserService userService;
    @Autowired
    private ReportService reportService;
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsersWithReports(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) List<User.AccountStatus> statuses,
            @RequestParam(required = false) Boolean reported
    ) {
        List<UserWithReportsDTO> users = userService
                .getWithFilters(search, roles, statuses, reported)
                .stream().map(user ->
                        new UserWithReportsDTO(user, reportService.findByReportedId(user.getId()))
                ).toList();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findUserWithReports(@PathVariable("id") Long id) {
        UserWithReportsDTO user = new UserWithReportsDTO(
                userService.findById(id), reportService.findByReportedId(id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/review/{id}")
    public ResponseEntity<?> reportReview(@PathVariable Long id) {
        reviewService.reportReview(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/user")
    public ResponseEntity<?> reportUser(@RequestBody UserReportSubmissionDTO dto) {
        User submitter = userService.findById(dto.getSubmitterId());
        if (submitter.hasRole("HOST")) {
            reportService.createHostReportGuest(new UserReport(-1L,
                    dto.getDescription(),
                    LocalDateTime.now(),
                    userService.findById(dto.getSubmitterId()),
                    userService.findById(dto.getReportedId())));
        } else {
            reportService.createGuestReportHost(new UserReport(-1L,
                    dto.getDescription(),
                    LocalDateTime.now(),
                    userService.findById(dto.getSubmitterId()),
                    userService.findById(dto.getReportedId())));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
