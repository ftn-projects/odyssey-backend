package com.example.odyssey.services;

import com.example.odyssey.entity.reports.UserReport;
import com.example.odyssey.entity.reservations.Reservation;
import com.example.odyssey.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReservationService reservationService;

    public void create(UserReport report) {
        reportRepository.save(report);
    }

    public void deleteUserReport(Long id) {
        reportRepository.deleteById(id);
    }

    public List<UserReport> findByReportedId(Long id) {
        return reportRepository.findByReportedId(id);
    }

    public void createHostReportGuest(UserReport userReport) {
        boolean canReport = false;
        for (Reservation r : reservationService.findByHost(userReport.getSubmitter().getId())) {
            if (r.getGuest().getId().equals(userReport.getReported().getId()) &&
                    r.getTimeSlot().getStart().isBefore(LocalDateTime.now()) &&
                    r.getStatus().equals(Reservation.Status.ACCEPTED))
                canReport = true;
        }

        if (canReport)
            reportRepository.save(userReport);
        else throw new UnsupportedOperationException("Guest has not stayed at your place before.");
    }

    public void createGuestReportHost(UserReport userReport) {
        boolean canReport = false;
        for (Reservation r : reservationService.findByGuest(userReport.getSubmitter().getId())) {
            if (r.getAccommodation().getHost().getId().equals(userReport.getReported().getId()) &&
                    r.getTimeSlot().getStart().isBefore(LocalDateTime.now()) &&
                    r.getStatus().equals(Reservation.Status.ACCEPTED))
                canReport = true;
        }

        if (canReport)
            reportRepository.save(userReport);
        else throw new UnsupportedOperationException("You have not stayed at the host place before.");
    }
}
