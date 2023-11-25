package com.example.odyssey.controllers;

import com.example.odyssey.dtos.notifications.NotificationDTO;
import com.example.odyssey.entity.notifications.Notification;
import com.example.odyssey.mappers.NotificationDTOMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final List<Notification> data = DummyData.getNotifications();

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAll() {
        List<Notification> notifications = data;

//        notifications = service.getAll();

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> findById(@PathVariable Long id) {
        Notification notification = data.get(0);

//        notification = service.findById(id);

        return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<NotificationDTO>> findByUserId(@PathVariable Long id) {
        List<Notification> notifications = data.subList(1, 3);

//        notifications = service.findByUserId(id);

        return new ResponseEntity<>(mapToDTO(notifications), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> readNotification(@PathVariable Long id) {
        Notification notification = data.get(0);

//        notification = service.readNotification(id);

        if (notification != null) {
            return new ResponseEntity<>(NotificationDTOMapper.fromNotificationToDTO(notification), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private static List<NotificationDTO> mapToDTO(List<Notification> notifications) {
        return notifications.stream().map(NotificationDTO::new).toList();
    }
}
