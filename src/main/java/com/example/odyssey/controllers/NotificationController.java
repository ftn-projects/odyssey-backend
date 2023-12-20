package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.mappers.NotificationDTOMapper;
import com.example.odyssey.mappers.UserDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/notifications")
public class NotificationController {
//    @Autowired
//    private NotificationService service;
//
//    @Autowired
//    public NotificationController(NotificationService service) {
//        this.service = service;
//    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Notification> notifications = new ArrayList<>();

//        notifications = service.getAll();

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Notification notification = new Notification();

//        notification = service.findById(id);

        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/user/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Long id) {
        List<Notification> notifications = new ArrayList<>();

//        notifications = service.findByUserId(id);

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = NotificationDTOMapper.fromDTOtoNotification(notificationDTO);

        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> readNotification(@PathVariable Long id) {
        Notification notification = new Notification();

//        notification = service.readNotification(id);

        if (notification != null) {
            return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static List<?> mapToDTO(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::new).toList();
    }
}
