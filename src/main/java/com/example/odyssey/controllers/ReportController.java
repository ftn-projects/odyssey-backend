package com.example.odyssey.controllers;

import com.example.odyssey.dtos.reports.UserReportDTO;
import com.example.odyssey.dtos.reports.UserReportSubmissionDTO;
import com.example.odyssey.dtos.users.UserWithReportsDTO;
import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reviews.Review;
import com.example.odyssey.entity.users.Host;
import com.example.odyssey.entity.users.User;
import com.example.odyssey.mappers.ReportDTOMapper;
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

    @PreAuthorize("hasAuthority('ADMIN')")
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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/review/{id}")
    public ResponseEntity<?> reportReview(@PathVariable Long id) {
        reviewService.reportReview(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/user")
    public ResponseEntity<?> reportUser(@RequestBody UserReportSubmissionDTO dto) {
        reportService.create(new UserReport(-1L,
                dto.getDescription(),
                LocalDateTime.now(),
                userService.findById(dto.getSubmitterId()),
                userService.findById(dto.getReportedId())));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
