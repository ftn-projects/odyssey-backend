package com.example.odyssey.controllers;

import com.example.odyssey.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class SchedulerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService service;

    @Scheduled(initialDelayString = "0", fixedRateString = "5000")
    public void fixedRateJobWithInitialDelay() {
//        logger.info("> fixedRateJobWithInitialDelay");
        service.deleteExpiredAccounts();
//        logger.info("< fixedRateJobWithInitialDelay");
    }
}
