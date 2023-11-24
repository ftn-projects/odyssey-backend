package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notifications")
public class NotificationController {


    //@Autowired
    //private NotificationService notificationService;

//    @Autowired
//    public NotificationController(NotificationService notificationService) {
//        this.notificationService = notificationService;
//    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(){
        List<NotificationDTO> dummyData = new ArrayList<>();
        //dummy data = notificationService.getAll();
        return new ResponseEntity<>(dummyData, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> getNotificationByID(Integer id){
        NotificationDTO dummyData = new NotificationDTO();
        //dummy data = notificationService.getOne(id);
        return new ResponseEntity<>(dummyData, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserAndTypes(
            @PathVariable Long userId,
            @RequestParam(required = false) List<Integer> notificationTypes
    ) {
        List<NotificationDTO> notifications;
        if (notificationTypes != null && !notificationTypes.isEmpty()) {
            notifications = new ArrayList<>();
        } else {
            notifications = new ArrayList<>();
        }
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(@PathVariable Long id) {
        NotificationDTO updatedNotification = new NotificationDTO();
        //updatedNotification = notificationService.updateNotification(id);
        //This is for setting the notification as read, service method name is placeholder
        if (updatedNotification != null) {
            return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
