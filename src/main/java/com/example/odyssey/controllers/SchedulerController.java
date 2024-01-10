package com.example.odyssey.controllers;

import com.example.odyssey.services.ReservationService;
import com.example.odyssey.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class SchedulerController {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Autowired
    private ReservationService reservationService;

    @Scheduled(initialDelayString = "0", fixedRateString = "500000")
    public void fixedRateJobWithInitialDelay() {
        userService.deleteExpiredAccounts();
        reservationService.declineExpiredReservations();
    }
}
